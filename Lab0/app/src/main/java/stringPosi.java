/**
 * Created by jinmingzhang on 11/5/16.
 */
public class stringPosi {

        public static String Returnfirst3(String str){
            int firstSpaceIndex = str.indexOf(" ");
            String firstThree = str.substring(firstSpaceIndex+1, firstSpaceIndex+4);
            return firstThree;
        }

        public static void main(String args[]) {

            String str1 = "I simply love";
            String str2 = "Java Programming and ";
            String str3 = "Android Studio IDE.";

            //int firstSpaceIndex = str1.indexOf("");

            System.out.println(Returnfirst3(str1)+ " "+ Returnfirst3(str2)+" "+Returnfirst3(str3));
        }
}
