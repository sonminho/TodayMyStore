import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int getGCD(int a, int b) {
		if(b == 0)
			return a;
		else
			return getGCD(b, a%b);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
	
		while(T-- > 0) {
			StringTokenizer stk = new StringTokenizer(br.readLine(), " ");
			
			int n = Integer.parseInt(stk.nextToken());
			int[] a = new int[n];
			
			long ans = 0;
			
			for(int i = 0; i < n; i++) {
				a[i] = Integer.parseInt(stk.nextToken());
			}
						
			for(int i = 0; i < n-1; i++) {
				for(int j = i + 1; j < n; j++){
					ans += getGCD(a[i], a[j]);
				}
			}
			System.out.println(ans);
		}
	}
}
