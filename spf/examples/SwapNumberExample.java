public class SwapNumberExample {                       
        public void test(int x, int y) {     
			if(x > y){
				x = x + y;
				y = x - y;
				x = x - y;
			if(x > y){
				assert false;
			}
		}
	}                 

        public static void main(String[] args) {    
		SwapNumberExample numb = new SwapNumberExample();			
	    	numb.test(1,2);
        }                 
}                         


