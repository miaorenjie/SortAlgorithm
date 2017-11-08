public class SortUtil {

    public static void mergeSort(int []src){
        mergeSort(src,0,src.length-1);
    }
    public static void mergeSort(int []src,int start,int end){
        int mid=(start+end)/2;
        if (start<=end)
            return;
        mergeSort(src,start,mid);
        mergeSort(src,mid+1,end);
        merge(src,start,end);
    }

    private static void merge(int[] src, int start, int end) {

        int mid=(start+end)/2;
        if(src[mid]<src[mid+1])
            return;
        int []temp=new int[end-start+1];
        int i=start,j=mid+1;
        int k=0;
        while (i<=mid||j<=end){
            if(i==mid){
                while (j<=end){
                    temp[k]=src[j];
                    ++j;
                    ++k;
                }
                break;
            }

            if(j==end){
                while (i<=mid){
                    temp[k]=src[i];
                    i++;
                    k++;
                }
                break;
            }
            if(src[i]>src[j]) {
                temp[k]=src[j];
                j++;
            }
            else{
                temp[k]=src[i];
                i++;
            }
            k++;
        }

    }

    /**
     * 快速排序
     * @param src
     */
    public static void quickSort(int []src){
        Main.printfln("1");
        quickSort(src,0,src.length-1);
    }
    private static void quickSort(int[] src, int low, int high){
        Main.printfln("2");
        if(low<high){

            int interval=quickPass(src,low,high);
            Main.printfln(src[interval]);
            quickSort(src,low,interval-1);
            quickSort(src,interval+1,high);

        }else
            return;

    }

    private static int quickPass(int[] src, int low, int high){
        Main.printfln("3");
        int interval=src[low];
        while (low<high){
            while (high>low)
            {
                if (src[high]<interval){
                    src[low]=src[high];
                    break;
                }
                high--;
            }
            while (low<high)
            {
                if (src[low]>interval){
                    src[high]=src[low];
                    break;
                }
                low++;
            }

        }
        src[low]=interval;
        Main.printfln(interval);
        return low;
    }
    /**
     * 插入排序
     * @param src
     * @param index
     * @param interval
     */
    public static void insertSort(int []src,int index,int interval){
        if (src==null)
            return;
        if (src.length<=1)
            return;
        int pivot=src[0];
        for(int i=index;i<src.length;i+=interval){

            pivot=src[i];
            Main.printfln(pivot);
            int j;
            if (i>0)
                if (pivot>src[i-1])
                    continue;
            for(j=index;j<i-1;j+=interval)
                if(pivot<src[j])
                    break;
            for(int k=i;k>j;k-=interval)
                src[k]=src[k-interval];
            src[j]=pivot;
            for (int ii=0;ii<src.length;ii++)
                Main.printf(src[ii]+" ");
            System.out.println();
        }
    }

    /**
     * 希尔排序
     * @param src
     * @param interval
     */
    public static void shellSort(int []src,int interval){
         while (interval>=1){
             insertSort(src,0,interval);
             Main.printfln("差为："+interval);
             interval/=2;
         }
    }
}
