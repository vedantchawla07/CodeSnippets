import java.util.*;
import java.io.*;
class SegTree{
	static int seg[];
	static int a[];
	public static void build(int index, int low, int high){
		if(low == high){
			seg[index] = a[low];
			return;
		}
		int mid = low + (high - low) / 2;
		build(2 * index + 1, low, mid);
		build(2 * index + 2, mid + 1, high);
		seg[index] = Math.min(seg[2 * index + 1], seg[2 * index + 2]);
	}
	public static int query(int index, int low, int high, int l, int r){
		//determining no overlap
		if(low > r || high < l) return Integer.MAX_VALUE;
		//complete overlap
		if(low >= l && high <= r) return seg[index];
		int mid = low + (high - low) / 2;
		int left = query(2 * index + 1, low, mid, l, r);
		int right = query(2 * index + 2, mid + 1, high, l, r);
		return Math.min(left, right);

	}
	public static void pointUpdate(int desiredIndex, int newValue, int low, int high, int currIndex){
		if(low == high){
			seg[currIndex] = newValue;
			return;
		}
		int mid = low + (high - low)/ 2;
		if(desiredIndex <= mid)
			pointUpdate(desiredIndex, newValue, low , mid, 2 * currIndex + 1);
		else pointUpdate(desiredIndex, newValue, mid + 1, high, 2 * currIndex + 2);

		seg[currIndex] = Math.min(seg[2 * currIndex + 1], seg[2 * currIndex + 2]);
	}

	public static void main(String args[]){

		try{
			System.setIn(new FileInputStream("input.txt"));
			System.setOut(new PrintStream(new FileOutputStream("output.txt")));
		}catch(Exception e){

			System.err.println("Error");
		}

		Scanner sc = new Scanner(System.in);
		int n;
		n = sc.nextInt();

		a = new int[n];
		for(int i = 0; i < n; i++)
			a[i] = sc.nextInt();

		seg = new int[4 * n];
		Arrays.fill(seg, -1);

		build(0, 0, n - 1);
		System.out.println("Testing the queries");

		int q;
		q = sc.nextInt();
		int type;
		while(q-- > 0){
			type = sc.nextInt();
			if(type == 1){
				int l = sc.nextInt();
				int r = sc.nextInt();
				System.out.println(query(0, 0, n - 1, l, r));	
			}else{
				int desiredIndex = sc.nextInt();
				int newValue = sc.nextInt();
				pointUpdate(desiredIndex, newValue, 0, n - 1, 0);
				a[desiredIndex] = newValue;
			}

		}


	}
}
