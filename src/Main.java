public class Main {
    public static void main(String[] args) {
        int []a={5,7,2,1,3,6,9,8};
        SortUtil.mergeSort(a);
//        SortUtil.insertSort(a,0,1);
        for(int i=0;i<a.length;i++)
            printf(a[i]+" ");
    }
    public static void printf(Object o)
    {
        System.out.print(o);
    }
    public static void printfln(Object o)
    {
        System.out.println(o);
    }
}
