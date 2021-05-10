package gale_shapley;
import java.util.Random;
import java.util.Scanner;
public class Gale_Shapley
{
    int Size,engagedCount;
    //All the functions that we are going to use
    static String[][] menpreference;
    static String[][] womenpreference;
    static String[] menslist;
    static String[] womenslist;
    String[] womenPartner;
    boolean[] menEngaged;
    //We would be changing number of men and women
    static int N=10;
    static char M,W;
   static Random r=new Random();
   //The main algorithm where we would be checking for instances
    public Gale_Shapley(String[] menslist, String[] womenslist, String[][] menpreference, String[][] womenpreference)
    {
        this.menslist=menslist;
        this.womenslist=womenslist;
        this.menpreference=menpreference;
        this.womenpreference=womenpreference;
        this.Size=menpreference.length;
        menEngaged = new boolean[Size];
        womenPartner = new String[Size];
        engagedCount = 0;
        
    }
    //We are using the following function to generate the list of men and women
    public static  String[] generateList(char m)
    {
        char a=m;
        String [] list=new String[N];
        for(int i=0,count=1;i<N;i++ ,count++)
        {
            if(a=='M')
            {
                list[i]="M"+count;
                System.out.print(list[i]);
            }
            else if(a =='W')
            {
                list[i]="W"+count;
                System.out.print(list[i]);
            }
        }
        return list; 
    }
    //This function is used to create men's preference matrix
    public static  String[][] menspreference(String [] list)
    {
        String finalmenList [][]=new String[N][N];
        System.out.println("\nMen Preference");
        for(int i=0;i<list.length;i++)
        {
            String temp[]=ShuffleList(womenslist);
            System.out.println("");
            for(int j=0;j<temp.length;j++)
            {
                finalmenList[i][j]=temp[j];
                System.out.print(finalmenList[i][j]);
            }
        }
        return finalmenList;
    }
     //This function is used to create women's preference matrix
   public static  String[][] womenspreference(String [] list)
    {
        String finalwomenList [][]=new String[N][N];
        System.out.println("\nWomen Preference");
        for(int i=0;i<list.length;i++)
        {
            String temp[]=ShuffleList(menslist);
            System.out.println("");
            for(int j=0;j<temp.length;j++)
            {
                finalwomenList[i][j]=temp[j];
                System.out.print(finalwomenList[i][j]);
            }
        }
        return finalwomenList;
    }
  
    public static String[] ShuffleList(String list[])
    {
        for(int i=0;i<N;i++)
        {
            int random=r.nextInt(N);
            String temp=list[i];
            list[i]=list[random];
            list[random]=temp;
        }
        return list;
    }
    //We are calculating the pairs
    void CalculateMatches()
    {
        while (engagedCount<Size)
        {
            int free;
            for(free=0;free<Size;free++)
                if (!menEngaged[free])
                    break;
            for(int i=0;i<Size && !menEngaged[free]; i++)
            {
                int index = WomenChoice(menpreference[free][i]);
                if (womenPartner[index]==null)
                {
                    womenPartner[index]=menslist[free];
                    menEngaged[free] = true;
                    engagedCount++;
                }
                else
                {
                    String currentPartner = womenPartner[index];
                    if (Preference(currentPartner, menslist[free], index))
                    {
                        womenPartner[index] = menslist[free];
                        menEngaged[free] = true;
                        menEngaged[MensChoice(currentPartner)] = false;
                    }
                }
            }           
        }
        printCouples();
    }
    private void printCouples()
    {
        System.out.println("\n** The Couples are :*** ");
        for (int i=0;i<Size;i++)
        {
            System.out.println(womenPartner[i] +" "+ womenslist[i]);
        }
    }
    private int MensChoice(String currentPartner)
    {
        for(int i=0;i<Size;i++)
            if (menslist[i].equals(currentPartner))
                return i;
        return -1;
    }
    
    //We are checking the stable matching in the following here to check if the matches are stable
    //Q b)
    private boolean Preference(String currentPartner, String newPartner,int index)
    {
        for(int i=0;i<Size;i++)
         {
             if (womenpreference[index][i].equals(newPartner))
                return true;
             if (womenpreference[index][i].equals(currentPartner))
                return false;
          }
          return false;
    }
    private int WomenChoice(String string)
    {
        for (int i = 0; i < Size; i++)
        {
            if (womenslist[i].equals(string))
            return i;
        }
        return -1;
    }
    public static void main(String[] args) throws CloneNotSupportedException
    {
        Scanner sc=new Scanner(System.in);
        long startTime=System.nanoTime();
        System.out.println("Mens List");
        menslist=generateList('M');
        System.out.println("");
        System.out.println("Womens List");
        womenslist=generateList('W');
        menpreference=menspreference(womenslist);
        womenpreference=womenspreference(menslist);
               
        Gale_Shapley obj = new Gale_Shapley(menslist, womenslist, menpreference, womenpreference);
        obj.CalculateMatches();
        
        sc.close();
        long elapsedTime=System.nanoTime()-startTime;
        System.out.println((double)elapsedTime/10000+"seconds");
    }

}
