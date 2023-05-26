# توضیحات پروژه

## **بخش دوم**

برای پیدا کردن فرش های مشابه به هم از الگوریتم Sequence alignment استفاده شده
در کلاس Sequence alignment دو ارایه به عنوان ورودی وارد می شوند که با هم مقایسه می شوند
در نهایت بک نمره از نظر شباهت محاسبه می شود و بازگشت داده می شود.به طوری که اگر دو اتیم با هم برابر باشد ۱ به کل نمره اضافه می شود و اگر نباشد ۱ از نمره کم می شود

```
this.MATCH = 1;
this.MISMATCH = -1;
this.INDEL = -1;
```

در متد findSolution یک ماتریس ساخته می شود به طوری که یکی از اریه ها به عنوان col ها و یکی به عنوان row ها انتخاب می شود
و اولین row and col امتیاز دهی می شوند به طوری کی هر مدام یکی کمتر از ایتم قبلی خود هستند

```
 int[][] solution = new int[strand1.length + 1][strand2.length + 1];
    solution[0][0] = 0;

    for (int i = 1; i < strand2.length + 1; i++) {
      solution[0][i] = solution[0][i - 1] + INDEL;
    }

    for (int i = 1; i < strand1.length + 1; i++) {
      solution[i][0] = solution[i - 1][0] + INDEL;
    }
```

بعد تمام ایتم های دو ارایه ورودی را با هم مقایسه می کنیم و بیشترین مقدار

* Position to the left -1
* Position above -1
* Position top-left + 1

را در ماتریس جواب قرار می دهیم

```
    for (int i = 1; i < strand1.length + 1; i++) {
      for (int j = 1; j < strand2.length + 1; j++) {

        int matchValue;

        if (strand1[i - 1] == strand2[j - 1])
          matchValue = MATCH;
        else
          matchValue = MISMATCH;

        solution[i][j] = max(solution[i][j - 1] + INDEL, solution[i - 1][j] + INDEL,
            solution[i - 1][j - 1] + matchValue);
      }
    }
```

در نهایت نمره شباهات این دو در خانه

```
solution[solution.length - 1][solution[0].length - 1]
```

قرار می گیرد

چون نقشه فرش ها یک ماتریس است با استفاده یک الگورییتم انها را به یک ارایه یک بعدی تبدیل می کنیم

```

  private int[] to1DArray(int[][] map) {
    int size = 0;
    for (int i = 0; i < map.length; i++) {
      size += map[i].length;
    }
    int[] result = new int[size];
    int z = 0;
    for (int row = 0; row < map.length; row++) {
      for (int col = 0; col < map[row].length; col++) {
        result[z] = map[row][col];
        z++;
      }
    }
    return result;

  }

```

***مرتبه این الگوریتم از O($n^2$) است***

## **بخش چهارم**

در این بخش برای پیدا کردن مسیر از الگوریتم floyed استفاده شده است.
در کلاس FindPath این الگوریتم پیاده سازی شده است

سازنده کلاس ارایه های مورد نیاز را برای این الگوریتم ایجاد می کند

```
FindPath(int V, int[][] graph) {
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        dis[i][j] = graph[i][j];

        if (graph[i][j] == INF)
          Next[i][j] = -1;
        else
          Next[i][j] = j;
      }
    }
    floyd(V);
  }
```

در اخر سازنده متد floyd فراخوانی می شود تا ماتریس  Next و Des کامل شود

```
 private void floyd(int V) {
    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          if (dis[i][k] == INF ||
              dis[k][j] == INF)
            continue;

          if (dis[i][j] > dis[i][k] +
              dis[k][j]) {
            dis[i][j] = dis[i][k] +
                dis[k][j];
            Next[i][j] = Next[i][k];
          }
        }
      }
    }
  } k
```

و متد printPath برای پرینت فاصله بین دو نقطه است که در با یم حلقه که چک می کنند مه ایا به نقطه مورد نظر رسیده است یا خیر و در همین حین خانه هایی که رد کرده را چاپ می کند
```
  void printPath(int u,
      int v) {
    if (Next[u][v] == -1)
      return;

    System.out.print(u + " -> ");

    while (u != v) {
      u = Next[u][v];
      System.out.print(u + " -> ");
    }
    System.out.println("END");
  }

```

***مرتبه این الگوریتم از O($n^3$) است***

سایت هایی که از انها ایده گرفته شد:

* https://www.geeksforgeeks.org/
* https://en.wikipedia.org/wiki/Sequence_alignment
* https://globin.bx.psu.edu/courses/fall2001/DP.pdf
