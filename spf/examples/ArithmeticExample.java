public class ArithmeticExample {

    public void test(int x){
        int z;
        if(x >= 10){
            int y = x - 10;
            z = x / y;
        }
        else{
            //do something
        }
    }


    public static void main(String[] args){
        ArithmeticExample numb = new ArithmeticExample();
        numb.test(10);
        System.out.println("Ok");
    }
}