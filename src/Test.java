import java.util.ArrayList;

public class Test {

    int[] size=new int[2];

    public static void main(String[] args){
        ArrayList<Integer> obstacles = new ArrayList<>();

        obstacles.add(1);
        obstacles.add(2);
        obstacles.add(3);

        for (int i =0; i<3; i++){
            System.out.println(obstacles.get(i));
        }
        obstacles.remove(1);
        int j=1;
        for (int i =0; i<3-j; i++){
            System.out.println(obstacles.get(i));
        }

    }


}
