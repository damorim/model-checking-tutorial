/* Author: Tomas Balyo, KIT, Karlsruhe */
#include <vector>

#include "ipasir4j_Ipasir4jNative.h"
extern "C" {
	#include "ipasir.h"
}

using namespace std;

struct SolverInfo {
	SolverInfo(void* solver): solver(solver),interrupt(false) {	}
	void *solver;
	bool interrupt;
};

vector<SolverInfo*> solvers;

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    signature
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ipasir4j_Ipasir4jNative_signature(JNIEnv * env, jclass) {
	jstring result = env->NewStringUTF(ipasir_signature());
	return result;
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_ipasir4j_Ipasir4jNative_init(JNIEnv *, jclass) {
	void* solver = ipasir_init();
	jint id = solvers.size();
	SolverInfo* sip = new SolverInfo(solver);
	solvers.push_back(sip);
	return id;
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    release
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_ipasir4j_Ipasir4jNative_release(JNIEnv *, jclass, jint id) {
	void* solver = solvers[id]->solver;
	ipasir_release(solver);
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    add
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_ipasir4j_Ipasir4jNative_add(JNIEnv *, jclass, jint id, jint lit) {
	void* solver = solvers[id]->solver;
	ipasir_add(solver, lit);
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    assume
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_ipasir4j_Ipasir4jNative_assume(JNIEnv *, jclass, jint id, jint lit) {
	void* solver = solvers[id]->solver;
	ipasir_assume(solver, lit);
}

int termCallback(void* state) {
	SolverInfo* sip = (SolverInfo*)state;
	if (sip->interrupt) {
		return 1;
	} else {
		return 0;
	}
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    solve
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_ipasir4j_Ipasir4jNative_solve(JNIEnv *, jclass, jint id) {
	void* solver = solvers[id]->solver;

	solvers[id]->interrupt = false;
	ipasir_set_terminate(solver, solvers[id], termCallback);
	jint result = ipasir_solve(solver);
	ipasir_set_terminate(solver, NULL, NULL);
	return result;
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    val
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_ipasir4j_Ipasir4jNative_val(JNIEnv *, jclass, jint id, jint var) {
	void* solver = solvers[id]->solver;
	jint result = ipasir_val(solver, var);
	return result;
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    failed
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_ipasir4j_Ipasir4jNative_failed(JNIEnv *, jclass, jint id, jint lit) {
	void* solver = solvers[id]->solver;
	jint result = ipasir_failed(solver, lit);
	return result;
}

/*
 * Class:     ipasir4j_Ipasir4jNative
 * Method:    interrupt
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_ipasir4j_Ipasir4jNative_interrupt(JNIEnv *, jclass, jint id) {
	solvers[id]->interrupt = true;
}

