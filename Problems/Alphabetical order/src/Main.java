import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        int k = 1;
        int value = 0;
        //boolean out = true;
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strArr = str.split("\\s+");

        for (int i = 0; k < strArr.length; i++,k++) {
            value = strArr[i].compareTo(strArr[k]);
        }
        System.out.println(!(value>0));


        /*switch (value) {
            case 1:
                out = false;
                break;
        }
        System.out.println(out);
        System.out.println(value);*/
    }
}