/** @author Tomas Balyo, KIT, Karlsruhe */

#include <jni.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

extern "C" {
#include "ipasir.h"
}

struct SolverData {
	int id;
	void* callbackState;
	int(*callbackFunction)(void*);
	pthread_t thread;
};

JavaVM *vm = NULL;
jclass clazz;
jmethodID init;
jmethodID reset;
jmethodID add;
jmethodID assume;
jmethodID solve;
jmethodID val;
jmethodID failed;
jmethodID interrupt;

JNIEnv* initializeJavaConnection() {
	JNIEnv *env;
    JavaVMOption options[1];
    char classpath[] = "-Djava.class.path=org.sat4j.core.jar:.";
    options[0].optionString = classpath;
    JavaVMInitArgs vm_args;
    vm_args.version = JNI_VERSION_1_2;
    vm_args.ignoreUnrecognized = 1;
    vm_args.nOptions = 1;
    vm_args.options = options;
    // Construct a VM
    JNI_CreateJavaVM(&vm, (void **)&env, &vm_args);
    // Get the class that contains the methods
    clazz = env->FindClass("Sat4jIpasir");
    if (clazz == NULL) {
    	printf("ERROR: jni initialization failed, class 'Sat4jIpasir' not found.\n");
    	return NULL;
    }
    // Get the method IDs
    init = env->GetStaticMethodID(clazz, "initSolver", "()I");
    reset = env->GetStaticMethodID(clazz, "reset", "(I)V");
    add = env->GetStaticMethodID(clazz, "add", "(II)V");
    assume = env->GetStaticMethodID(clazz, "assume", "(II)V");
    solve = env->GetStaticMethodID(clazz, "solve", "(I)I");
    val = env->GetStaticMethodID(clazz, "val", "(II)I");
    failed = env->GetStaticMethodID(clazz, "failed", "(II)I");
    interrupt = env->GetStaticMethodID(clazz, "terminate", "(I)V");

    if (init == NULL || reset == NULL || add == NULL || assume == NULL
    		|| solve == NULL || val == NULL || failed == NULL || interrupt == NULL) {
    	printf("ERROR: jni initialization failed, method not found.\n");
    }
    return env;
    //vm->DestroyJavaVM();
}

JNIEnv* getEnv() {
	JNIEnv *env = NULL;
	if (vm == NULL) {
		env = initializeJavaConnection();
	} else {
		jint res = vm->GetEnv((void**)&env, JNI_VERSION_1_2);
		if (res == JNI_EDETACHED) {
			vm->AttachCurrentThread((void**)&env, NULL);
		}
	}
	return env;
}

/**
 * Return the name and the version of the solver
 */
const char * ipasir_signature () {
	return "Sat4j-2.3.5.";
}

/**
 * Construct a new solver and return a pointer to it
 */
void * ipasir_init () {
	JNIEnv *env = getEnv();
    jint result  = env->CallStaticIntMethod(clazz, init);
    SolverData* sd = (SolverData*)malloc(sizeof(SolverData));
    sd->id = (int)result;
    sd->callbackFunction = NULL;
    sd->callbackState = NULL;
    return sd;
}

/**
 * Release all the allocated memory (destructor)
 */
void ipasir_release (void * solver) {
	SolverData* sd = (SolverData*)solver;
	jint id = sd->id;
	getEnv()->CallStaticVoidMethod(clazz, reset, id);
	free(sd);
}

/**
 * Add literal into the currently added clause or finalize the clause with 0
 */
void ipasir_add (void * solver, int lit) {
	SolverData* sd = (SolverData*)solver;
	getEnv()->CallStaticVoidMethod(clazz, add, sd->id, lit);
}

/**
 * Add an assumption for solving
 */
void ipasir_assume (void * solver, int lit) {
	SolverData* sd = (SolverData*)solver;
	getEnv()->CallStaticVoidMethod(clazz, assume, sd->id, lit);
}

/**
 * Get the truth value of the given literal.
 * Return 'lit' if True, '-lit' if False, and 0 if not important
 */
int ipasir_val (void * solver, int lit) {
	SolverData* sd = (SolverData*)solver;
    jint result = getEnv()->CallStaticIntMethod(clazz, val, sd->id, lit);
    return result;
}

/**
 * Check if the given assumption literal has failed.
 * Return 1 if failed, 0 otherwise.
 */
int ipasir_failed (void * solver, int lit) {
	SolverData* sd = (SolverData*)solver;
    jint result = getEnv()->CallStaticIntMethod(clazz, failed, sd->id, lit);
    return result;
}

void * terminateCheckThread(void * solver) {
	SolverData* sd = (SolverData*)solver;
	while (true) {
		usleep(100*1000); //100 miliseconds
		if (sd->callbackFunction(sd->callbackState)) {
			getEnv()->CallStaticVoidMethod(clazz, interrupt, sd->id);
		}
	}
	return NULL;
}

/**
 * Solve the formula with specified clauses under the specified assumptions.
 * Returns 10 for SAT, 20 for UNSAT, and 0 for INDETERMINATE
 */
int ipasir_solve (void * solver) {
	SolverData* sd = (SolverData*)solver;
	if (sd->callbackFunction != NULL) {
		pthread_create(&(sd->thread), NULL, terminateCheckThread, sd);
	}
    jint result = getEnv()->CallStaticIntMethod(clazz, solve, sd->id);
	if (sd->callbackFunction != NULL) {
		pthread_cancel(sd->thread);
	}
    return result;
}

void ipasir_set_terminate (void * solver, void * state, int (*terminate)(void * state)) {
	SolverData* sd = (SolverData*)solver;
	sd->callbackFunction = terminate;
	sd->callbackState = state;
}
