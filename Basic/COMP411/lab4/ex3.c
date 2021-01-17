#include<stdio.h>

int end = 1;
int sum = 0;
int NchooseK(int n, int k){
  if(n == 0 || k == 0 || n == k){
    return 1;
  }
  sum++;
  return (NchooseK(n - 1 , k - 1) + NchooseK(n - 1, k));
}

int main(){
  int n, k;
  while( end != 0){
    puts("Enter two integers (for n and k) separated by space:");
    scanf("%d %d", &n, &k);
    NchooseK(n, k);
    if(n == 0 && k == 0){
      end = 0;
      printf("1\n");
    } else {
    printf("%d\n", sum + 1);
    sum = 0;
    }
  }
}