## 使用GC（串行/并行/CMS/G1）对JVM调优总结

### 背景介绍

1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。

```java
public class GCLogAnalysis {
    private static Random random = new Random();
    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("runing...");
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        while (System.currentTimeMillis() < endMillis) {
            Object garbage = generateGarbage(100*1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("over!create object count number is:" + counter.longValue());
    }
    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}
```

2、使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar示例。

### 演示过程

**概述**：分别对GCLogAnalysis.java和gateway-server-0.0.1-SNAPSHOT.jar使用不同的GC启动，并对启动的堆内存大小进行设置，打印GC日志，根据GC日志分析GC执行情况，找到不同堆内存大小的对各个GC之间 的关系

##### 环境 

```
JDK8 
```

#### 串行GC 

###### 实验一

```properties
java -Xms128m -Xmx512m -XX:+UseSerialGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  34944K->12315K(126720K), 0.0099948 secs]
[GC (Allocation Failure)  47227K->24996K(126720K), 0.0150012 secs]
[GC (Allocation Failure)  59432K->36412K(126720K), 0.0089647 secs]
[GC (Allocation Failure)  71339K->47340K(126720K), 0.0102568 secs]
[GC (Allocation Failure)  81820K->59474K(126720K), 0.0094888 secs]
[GC (Allocation Failure)  94418K->70564K(126720K), 0.0095311 secs]
[GC (Allocation Failure)  105143K->81410K(126720K), 0.0103595 secs]
[GC (Allocation Failure)  115754K->91350K(126720K), 0.0101642 secs]
[GC (Allocation Failure)  126294K->99505K(134672K), 0.0079003 secs]
[Full GC (Allocation Failure)  99505K->93867K(134672K), 0.0156967 secs]
[GC (Allocation Failure)  156508K->114768K(226912K), 0.0156974 secs]
[GC (Allocation Failure)  177424K->137315K(226912K), 0.0223033 secs]
[GC (Allocation Failure)  199971K->157826K(226912K), 0.0163800 secs]
[GC (Allocation Failure)  220482K->179885K(242576K), 0.0183245 secs]
[Full GC (Allocation Failure)  179885K->158943K(242576K), 0.0235894 secs]
[GC (Allocation Failure)  264927K->194014K(384140K), 0.0174495 secs]
[GC (Allocation Failure)  299853K->225287K(384140K), 0.0343001 secs]
[GC (Allocation Failure)  331271K->255888K(384140K), 0.0251575 secs]
[GC (Allocation Failure)  361872K->291288K(397284K), 0.0288935 secs]
[Full GC (Allocation Failure)  291288K->234512K(397284K), 0.0409151 secs]
[GC (Allocation Failure)  374288K->277805K(506816K), 0.0177974 secs]
[GC (Allocation Failure)  417522K->316352K(506816K), 0.0330762 secs]
[GC (Allocation Failure)  456128K->362367K(506816K), 0.0396803 secs]
[Full GC (Allocation Failure)  502030K->282600K(506816K), 0.0486720 secs]
[GC (Allocation Failure)  422164K->330465K(506816K), 0.0077348 secs]
[Full GC (Allocation Failure)  470183K->329914K(506816K), 0.0388119 secs]
[Full GC (Allocation Failure)  469690K->329091K(506816K), 0.0476107 secs]
over!create object count number is:7405
```

Full GC 频率逐渐增加，且每次Full GC时间呈上升趋势，young GC 频率一直趋于平衡，

###### 实验二

```properties
java -Xms128m -Xmx1g -XX:+UseSerialGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  34944K->12871K(126720K), 0.0116738 secs]
[GC (Allocation Failure)  47593K->26890K(126720K), 0.0156821 secs]
[GC (Allocation Failure)  61772K->41129K(126720K), 0.0121279 secs]
[GC (Allocation Failure)  75815K->51399K(126720K), 0.0096708 secs]
[GC (Allocation Failure)  86335K->63621K(126720K), 0.0106908 secs]
[GC (Allocation Failure)  98565K->75617K(126720K), 0.0116146 secs]
[GC (Allocation Failure)  110292K->88086K(126720K), 0.0104153 secs]
[GC (Allocation Failure)  123030K->98517K(133464K), 0.0081756 secs]
[Full GC (Allocation Failure)  98517K->94004K(133464K), 0.0134360 secs]
[GC (Allocation Failure)  156788K->111229K(227268K), 0.0124960 secs]
[GC (Allocation Failure)  174013K->132904K(227268K), 0.0232923 secs]
[GC (Allocation Failure)  195688K->151107K(227268K), 0.0180919 secs]
[GC (Allocation Failure)  213891K->173103K(236004K), 0.0185367 secs]
[Full GC (Allocation Failure)  173103K->152102K(236004K), 0.0270086 secs]
[GC (Allocation Failure)  253499K->183261K(367616K), 0.0158332 secs]
[GC (Allocation Failure)  284671K->218394K(367616K), 0.0373801 secs]
[GC (Allocation Failure)  319834K->257046K(367616K), 0.0339033 secs]
[GC (Allocation Failure)  358395K->288771K(390356K), 0.0279604 secs]
[Full GC (Allocation Failure)  288771K->237458K(390356K), 0.0357893 secs]
[GC (Allocation Failure)  395858K->293935K(573944K), 0.0230335 secs]
[GC (Allocation Failure)  452335K->340441K(573944K), 0.0554129 secs]
[GC (Allocation Failure)  498718K->388493K(573944K), 0.0447196 secs]
[GC (Allocation Failure)  546893K->440363K(598896K), 0.0475430 secs]
[Full GC (Allocation Failure)  440363K->293119K(598896K), 0.0497872 secs]
[GC (Allocation Failure)  488639K->352497K(708436K), 0.0291737 secs]
over!create object count number is:6651
```

GC频率下降，但效果并不明显，吞吐量还下降了，Full GC 频率和时长继续增加

###### 实验三

```properties
java -Xms128m -Xmx2g -XX:+UseSerialGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  34936K->10948K(126720K), 0.0088942 secs]
[GC (Allocation Failure)  45884K->20768K(126720K), 0.0120796 secs]
[GC (Allocation Failure)  55681K->31499K(126720K), 0.0101663 secs]
[GC (Allocation Failure)  66443K->44167K(126720K), 0.0120122 secs]
[GC (Allocation Failure)  78953K->58001K(126720K), 0.0111039 secs]
[GC (Allocation Failure)  92663K->69499K(126720K), 0.0107396 secs]
[GC (Allocation Failure)  104232K->79716K(126720K), 0.0100794 secs]
[GC (Allocation Failure)  114660K->90570K(126720K), 0.0095035 secs]
[GC (Allocation Failure)  125441K->103309K(138328K), 0.0125052 secs]
[Full GC (Allocation Failure)  103309K->94902K(138328K), 0.0171833 secs]
[GC (Allocation Failure)  158237K->116738K(229404K), 0.0153704 secs]
[GC (Allocation Failure)  179469K->139902K(229404K), 0.0248893 secs]
[GC (Allocation Failure)  203247K->162856K(229404K), 0.0175914 secs]
[GC (Allocation Failure)  225988K->182848K(246244K), 0.0165479 secs]
[Full GC (Allocation Failure)  182848K->162160K(246244K), 0.0282545 secs]
[GC (Allocation Failure)  270320K->204350K(391932K), 0.0241807 secs]
[GC (Allocation Failure)  312510K->238136K(391932K), 0.0358866 secs]
[GC (Allocation Failure)  346227K->274666K(391932K), 0.0290539 secs]
[GC (Allocation Failure)  382683K->308493K(416772K), 0.0311202 secs]
[Full GC (Allocation Failure)  308493K->247919K(416772K), 0.0383850 secs]
[GC (Allocation Failure)  413359K->299786K(599248K), 0.0208784 secs]
[GC (Allocation Failure)  465226K->345955K(599248K), 0.0402282 secs]
[GC (Allocation Failure)  511395K->399484K(599248K), 0.0459582 secs]
[GC (Allocation Failure)  564924K->451260K(616836K), 0.0457870 secs]
[Full GC (Allocation Failure)  451260K->301742K(616836K), 0.0534320 secs]
over!create object count number is:6409
```

GC频率几乎没有下降，吞吐量又下降了，Full GC 频率和时长继续增加

###### 实验四

```properties
java -Xms128m -Xmx6g -XX:+UseSerialGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  34613K->16160K(126720K), 0.0145154 secs]
[GC (Allocation Failure)  51104K->27053K(126720K), 0.0131672 secs]
[GC (Allocation Failure)  61997K->40490K(126720K), 0.0112592 secs]
[GC (Allocation Failure)  75434K->49571K(126720K), 0.0077711 secs]
[GC (Allocation Failure)  84515K->61062K(126720K), 0.0094091 secs]
[GC (Allocation Failure)  95345K->74278K(126720K), 0.0125971 secs]
[GC (Allocation Failure)  109090K->84200K(126720K), 0.0081987 secs]
[GC (Allocation Failure)  119089K->95999K(131052K), 0.0121893 secs]
[Full GC (Allocation Failure)  95999K->91098K(131052K), 0.0114310 secs]
[GC (Allocation Failure)  151903K->112905K(220248K), 0.0172162 secs]
[GC (Allocation Failure)  173769K->132518K(220248K), 0.0250977 secs]
[GC (Allocation Failure)  193307K->154215K(220248K), 0.0181315 secs]
[GC (Allocation Failure)  214783K->176879K(237784K), 0.0216854 secs]
[Full GC (Allocation Failure)  176879K->157686K(237784K), 0.0215529 secs]
[GC (Allocation Failure)  262730K->194661K(381148K), 0.0207502 secs]
[GC (Allocation Failure)  299877K->232542K(381148K), 0.0402332 secs]
[GC (Allocation Failure)  337683K->266962K(381148K), 0.0289952 secs]
[GC (Allocation Failure)  372178K->305656K(411024K), 0.0312887 secs]
[Full GC (Allocation Failure)  305656K->247634K(411024K), 0.0414412 secs]
[GC (Allocation Failure)  412818K->301747K(598516K), 0.0222814 secs]
[GC (Allocation Failure)  466931K->350380K(598516K), 0.0469095 secs]
[GC (Allocation Failure)  515564K->396370K(598516K), 0.0428140 secs]
[GC (Allocation Failure)  561554K->445270K(610560K), 0.0445860 secs]
[Full GC (Allocation Failure)  445270K->305424K(610560K), 0.0494897 secs]
[GC (Allocation Failure)  509136K->367379K(738164K), 0.0267463 secs]
over!create object count number is:6732
```

不禁怀疑，串行GC对堆内存增加到一定程度后无感。

#### 并行GC

###### 测试一、

```properties
java -Xms128 -Xmx128 -XX:+UseParallelGC GCLogAnalysis
java -Xms512 -Xmx512 -XX:+UseParallelGC GCLogAnalysis
# 结果
Error occurred during initialization of VM
Too small initial heap
```

结果显示堆内存设置过小

###### 测试二

```properties
 java -Xms128m -Xmx128m -XX:+UseParallelGC GCLogAnalysis
 # 结果
 Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:40)
        at GCLogAnalysis.main(GCLogAnalysis.java:23)
```

据测试一和二可猜测，xms设置过小可能会导致堆内存不够无法启动，同时可大胆猜测若不写设置对内存大小是不写单位默认为bit

###### 测试三

```properties
 java -Xms128m -Xmx512m -XX:+UseParallelGC -XX:+PrintGC GCLogAnalysis
 # 结果
 [GC (Allocation Failure)  33255K->11466K(125952K), 0.0039682 secs]
[GC (Allocation Failure)  44612K->22392K(159232K), 0.0042344 secs]
[GC (Allocation Failure)  88467K->45637K(159232K), 0.0070986 secs]
[GC (Allocation Failure)  112197K->67103K(225792K), 0.0068047 secs]
[Full GC (Ergonomics)  67103K->61711K(270336K), 0.0129508 secs]
[GC (Allocation Failure)  194831K->105731K(270336K), 0.0105903 secs]
[Full GC (Ergonomics)  105731K->99631K(339456K), 0.0156583 secs]
[GC (Allocation Failure)  232751K->142812K(303616K), 0.0093144 secs]
[GC (Allocation Failure)  208860K->162240K(321536K), 0.0068705 secs]
[GC (Allocation Failure)  228288K->179357K(314368K), 0.0134878 secs]
[GC (Allocation Failure)  238237K->194790K(317952K), 0.0099454 secs]
[GC (Allocation Failure)  253620K->214214K(317952K), 0.0105733 secs]
[Full GC (Ergonomics)  214214K->181791K(408576K), 0.0237057 secs]
[GC (Allocation Failure)  240671K->203827K(408576K), 0.0028059 secs]
[GC (Allocation Failure)  262707K->220940K(408576K), 0.0061827 secs]
[GC (Allocation Failure)  279820K->238067K(408576K), 0.0056697 secs]
[GC (Allocation Failure)  296913K->256776K(408576K), 0.0059155 secs]
[GC (Allocation Failure)  315487K->277582K(408576K), 0.0079569 secs]
[GC (Allocation Failure)  336462K->296985K(408576K), 0.0072576 secs]
[Full GC (Ergonomics)  296985K->235466K(466432K), 0.0334109 secs]
[GC (Allocation Failure)  294271K->258744K(466432K), 0.0034829 secs]
[GC (Allocation Failure)  317582K->275466K(466432K), 0.0060755 secs]
[GC (Allocation Failure)  334138K->298883K(466432K), 0.0063933 secs]
[GC (Allocation Failure)  357763K->319292K(464384K), 0.0086967 secs]
[GC (Allocation Failure)  377852K->337646K(465408K), 0.0083857 secs]
[GC (Allocation Failure)  396526K->358845K(464384K), 0.0083774 secs]
[Full GC (Ergonomics)  358845K->277239K(464384K), 0.0436816 secs]
[GC (Allocation Failure)  340042K->299384K(465408K), 0.0045161 secs]
[GC (Allocation Failure)  362264K->315362K(469504K), 0.0077430 secs]
[GC (Allocation Failure)  386512K->337067K(470016K), 0.0047185 secs]
[GC (Allocation Failure)  408235K->357144K(472064K), 0.0048754 secs]
[Full GC (Ergonomics)  357144K->301647K(472064K), 0.0403315 secs]
[GC (Allocation Failure)  379345K->326178K(473088K), 0.0040474 secs]
[GC (Allocation Failure)  403882K->355298K(477184K), 0.0080119 secs]
[Full GC (Ergonomics)  355298K->311881K(477184K), 0.0445808 secs]
[GC (Allocation Failure)  395849K->342651K(477696K), 0.0041444 secs]
[GC (Allocation Failure)  426604K->370575K(469504K), 0.0075686 secs]
[Full GC (Ergonomics)  370575K->321782K(469504K), 0.0469041 secs]
[GC (Allocation Failure)  396950K->350558K(474624K), 0.0035564 secs]
[GC (Allocation Failure)  425566K->370340K(464896K), 0.0062961 secs]
over!create object count number is:8361
```

粗略看出Full GC 频率逐渐增加，且每次Full GC时间呈上升趋势，young GC 频率一直趋于平衡，Full GC 约0.25s，young GC约0.2s，Full GC过于频繁,总体来看，相同堆内存下，并行GC效果比串行要好。

###### 实验四

```properties
java -Xms256m -Xmx1g -XX:+UseParallelGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  65536K->21814K(251392K), 0.0060361 secs]
[GC (Allocation Failure)  86843K->45369K(316928K), 0.0098572 secs]
[GC (Allocation Failure)  176441K->86680K(316928K), 0.0124058 secs]
[GC (Allocation Failure)  217752K->129255K(448000K), 0.0144971 secs]
[GC (Allocation Failure)  391399K->207568K(470016K), 0.0307620 secs]
[Full GC (Ergonomics)  207568K->177767K(612864K), 0.0288347 secs]
[GC (Allocation Failure)  439911K->261681K(551424K), 0.0220087 secs]
[GC (Allocation Failure)  396849K->295188K(582144K), 0.0139607 secs]
[GC (Allocation Failure)  430356K->324752K(563712K), 0.0213539 secs]
[GC (Allocation Failure)  441425K->352033K(572928K), 0.0224381 secs]
[GC (Allocation Failure)  468769K->378197K(572928K), 0.0229785 secs]
[Full GC (Ergonomics)  378197K->269364K(675328K), 0.0385638 secs]
[GC (Allocation Failure)  386100K->310097K(675328K), 0.0053821 secs]
[GC (Allocation Failure)  426833K->347485K(675328K), 0.0113631 secs]
[GC (Allocation Failure)  464052K->383115K(675328K), 0.0158990 secs]
[GC (Allocation Failure)  499851K->420733K(675328K), 0.0155303 secs]
[Full GC (Ergonomics)  420733K->314858K(727552K), 0.0444907 secs]
[GC (Allocation Failure)  431493K->352915K(727552K), 0.0061617 secs]
[GC (Allocation Failure)  469651K->387303K(727552K), 0.0089629 secs]
[GC (Allocation Failure)  504039K->425698K(727552K), 0.0103288 secs]
[GC (Allocation Failure)  542434K->460652K(727552K), 0.0186321 secs]
[GC (Allocation Failure)  577216K->495388K(650240K), 0.0149301 secs]
[Full GC (Ergonomics)  495388K->344617K(692736K), 0.0506796 secs]
over!create object count number is:9300
```

随着堆内存增多，创建对象的能力（吞吐量）增加，young GC 频率减少，Full GC 频率也减少，Full GC单次时长呈上升趋势，总体来看，相同堆内存下，并行GC效果比串行要好。

 ###### 实验五

```properties
java -Xms256m -Xmx2g -XX:+UseParallelGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  65536K->20330K(251392K), 0.0054671 secs]
[GC (Allocation Failure)  85866K->44425K(316928K), 0.0085203 secs]
[GC (Allocation Failure)  175497K->86975K(316928K), 0.0133898 secs]
[GC (Allocation Failure)  217873K->136472K(448000K), 0.0140401 secs]
[Full GC (Ergonomics)  136472K->125546K(539648K), 0.0192910 secs]
[GC (Allocation Failure)  387690K->213464K(539648K), 0.0214883 secs]
[Full GC (Ergonomics)  213464K->190562K(655872K), 0.0274685 secs]
[GC (Allocation Failure)  452706K->271382K(899584K), 0.0186390 secs]
[GC (Allocation Failure)  707094K->366871K(926720K), 0.0506056 secs]
[GC (Allocation Failure)  802583K->453197K(829440K), 0.0425641 secs]
[Full GC (Ergonomics)  453197K->298541K(953856K), 0.0484236 secs]
[GC (Allocation Failure)  589869K->382768K(1002496K), 0.0104947 secs]
[GC (Allocation Failure)  674096K->455160K(984064K), 0.0174619 secs]
over!create object count number is:9705
```

堆内存增加一个G，但效果不如预期，GC （Full GC and young GC）频率继续减少，但young GC 单次占用时长增加，Full GC单次时长呈上升趋势，总体来看，相同堆内存下，并行GC效果比串行要好。

###### 实验六

```properties
java -Xms256m -Xmx6g -XX:+UseParallelGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  65536K->19994K(251392K), 0.0060709 secs]
[GC (Allocation Failure)  85444K->40822K(316928K), 0.0076944 secs]
[GC (Allocation Failure)  171894K->80126K(316928K), 0.0123883 secs]
[GC (Allocation Failure)  211198K->118287K(448000K), 0.0121782 secs]
[GC (Allocation Failure)  380431K->196574K(459264K), 0.0270078 secs]
[Full GC (Ergonomics)  196574K->170605K(605184K), 0.0297155 secs]
[GC (Allocation Failure)  432749K->257375K(890368K), 0.0258057 secs]
[GC (Allocation Failure)  728415K->358937K(928768K), 0.0580438 secs]
[Full GC (Ergonomics)  358937K->273079K(1065984K), 0.0423846 secs]
[GC (Allocation Failure)  762039K->418012K(1463296K), 0.0457478 secs]
over!create object count number is:9332
```

索性对内存开到6G，但效果适得其反，GC次数如预期减少，但单次GC时长增加，尤其是young GC时长几乎是初始的10倍，Full GC单次时长呈上升趋势，总体来看，相同堆内存下，并行GC效果比串行要好。

###### 分析

以上六次实验，其中除Full GC单次时长呈上升趋势与程序中强引用对象一直增加未释放有关，其余现象可说明，堆内存大小设置，并不是越多越好，太大可以降低GC频率，但是会增加单次GC的时长，总体来看，相同堆内存下，并行GC效果比串行要好。

#### CMS

###### 实验一

```properties
 java -Xms128m -Xmx512m -XX:+UseConcMarkSweepGC -XX:+PrintGC GCLogAnalysis
 # 结果
 [GC (Allocation Failure)  34808K->11836K(126720K), 0.0041718 secs]
[GC (Allocation Failure)  46780K->23716K(126720K), 0.0069456 secs]
[GC (Allocation Failure)  58660K->36107K(126720K), 0.0090051 secs]
[GC (Allocation Failure)  71051K->45560K(126720K), 0.0067376 secs]
[GC (Allocation Failure)  80504K->57628K(126720K), 0.0078322 secs]
[GC (CMS Initial Mark)  58409K(126720K), 0.0014392 secs]
[GC (Allocation Failure)  92391K->69055K(126720K), 0.0081771 secs]
[GC (Allocation Failure)  103959K->77926K(126720K), 0.0062198 secs]
[GC (Allocation Failure)  112870K->91399K(127000K), 0.0084486 secs]
[GC (Allocation Failure)  126297K->101990K(137652K), 0.0075285 secs]
[GC (Allocation Failure)  136934K->114344K(149976K), 0.0087509 secs]
[GC (Allocation Failure)  149129K->126346K(161924K), 0.0110640 secs]
[GC (Allocation Failure)  161289K->138203K(173832K), 0.0080355 secs]
[GC (Allocation Failure)  172672K->147415K(183088K), 0.0092536 secs]
[GC (Allocation Failure)  181671K->159196K(194764K), 0.0075148 secs]
[GC (Allocation Failure)  194114K->175607K(211348K), 0.0116082 secs]
[GC (Allocation Failure)  210551K->187378K(223096K), 0.0085503 secs]
[GC (Allocation Failure)  222322K->196263K(231936K), 0.0067014 secs]
[GC (Allocation Failure)  231207K->211373K(247028K), 0.0104973 secs]
[GC (Allocation Failure)  245772K->221101K(256824K), 0.0077174 secs]
[GC (Allocation Failure)  255990K->232636K(268428K), 0.0097518 secs]
[GC (Allocation Failure)  267506K->246070K(281828K), 0.0099700 secs]
[GC (Allocation Failure)  280877K->259763K(295460K), 0.0101506 secs]
[GC (Allocation Failure)  294324K->272840K(308536K), 0.0121001 secs]
[GC (Allocation Failure)  307781K->283772K(319620K), 0.0098130 secs]
[GC (Allocation Failure)  318716K->296200K(332052K), 0.0116113 secs]
[GC (Allocation Failure)  331087K->307938K(343800K), 0.0099176 secs]
[GC (Allocation Failure)  342646K->321133K(356952K), 0.0112141 secs]
[GC (Allocation Failure)  355871K->334998K(370888K), 0.0115576 secs]
[GC (Allocation Failure)  369942K->345400K(381216K), 0.0114354 secs]
[Full GC (Allocation Failure)  380344K->233969K(381216K), 0.0397383 secs]
[GC (Allocation Failure)  373745K->278405K(506816K), 0.0105731 secs]
[GC (CMS Initial Mark)  278939K(506816K), 0.0010034 secs]
[GC (Allocation Failure)  418181K->325261K(506816K), 0.0141068 secs]
[Full GC (Allocation Failure)  464287K->274996K(506816K), 0.0469674 secs]
[GC (Allocation Failure)  414772K->319858K(506816K), 0.0066381 secs]
[GC (CMS Initial Mark)  320488K(506816K), 0.0029214 secs]
[GC (Allocation Failure)  459634K->366313K(506816K), 0.0142860 secs]
[GC (CMS Final Remark)  366371K(506816K), 0.0040692 secs]
[Full GC (Allocation Failure)  478309K->315124K(506816K), 0.0489864 secs]
[GC (CMS Initial Mark)  315277K(506816K), 0.0009834 secs]
[Full GC (Allocation Failure)  454900K->318922K(506816K), 0.0482265 secs]
over!create object count number is:7576
```

从数据运行来看，CMS young GC 频繁，但平均用时少，总体数据不如并行GC

###### 实验二

```properties
java -Xms128m -Xmx1g -XX:+UseConcMarkSweepGC -XX:+PrintGC GCLogAnalysis
# 结果
[GC (Allocation Failure)  34507K->11912K(126720K), 0.0033876 secs]
[GC (Allocation Failure)  46856K->26636K(126720K), 0.0070279 secs]
[GC (Allocation Failure)  61580K->42529K(126720K), 0.0109561 secs]
[GC (Allocation Failure)  77322K->54327K(126720K), 0.0077055 secs]
[GC (CMS Initial Mark)  55019K(126720K), 0.0013090 secs]
[GC (Allocation Failure)  89271K->67886K(126720K), 0.0094672 secs]
[GC (CMS Final Remark)  68795K(126720K), 0.0014264 secs]
[GC (Allocation Failure)  99525K->74637K(139688K), 0.0061586 secs]
[GC (CMS Initial Mark)  75765K(139688K), 0.0027933 secs]
[GC (Allocation Failure)  109262K->85584K(139688K), 0.0091530 secs]
[GC (Allocation Failure)  120064K->96309K(139688K), 0.0079580 secs]
[GC (Allocation Failure)  130931K->110081K(145508K), 0.0102945 secs]
[GC (Allocation Failure)  145025K->122527K(158068K), 0.0087817 secs]
[GC (Allocation Failure)  157457K->133772K(169312K), 0.0081119 secs]
[GC (Allocation Failure)  168716K->146046K(181552K), 0.0091152 secs]
[GC (Allocation Failure)  180237K->157663K(193100K), 0.0077842 secs]
[GC (Allocation Failure)  192480K->170234K(205732K), 0.0091608 secs]
[GC (Allocation Failure)  205028K->181583K(217060K), 0.0080719 secs]
[GC (Allocation Failure)  216119K->192755K(228168K), 0.0092643 secs]
[GC (Allocation Failure)  227699K->204359K(239800K), 0.0088839 secs]
[GC (Allocation Failure)  239184K->215948K(251464K), 0.0077979 secs]
[GC (Allocation Failure)  250892K->226351K(261800K), 0.0080810 secs]
[GC (Allocation Failure)  261295K->237615K(273148K), 0.0078422 secs]
[GC (Allocation Failure)  272411K->251215K(286792K), 0.0107728 secs]
[GC (Allocation Failure)  286159K->264366K(299936K), 0.0106266 secs]
[GC (Allocation Failure)  299117K->273783K(309412K), 0.0072819 secs]
[GC (Allocation Failure)  308448K->285514K(321200K), 0.0086442 secs]
[GC (Allocation Failure)  320345K->296434K(331992K), 0.0099471 secs]
[GC (Allocation Failure)  331378K->312731K(348336K), 0.0127799 secs]
[GC (Allocation Failure)  347524K->324373K(359912K), 0.0106759 secs]
[GC (Allocation Failure)  359129K->334302K(369844K), 0.0090491 secs]
[GC (Allocation Failure)  369218K->345621K(381292K), 0.0089741 secs]
[GC (Allocation Failure)  380504K->357339K(392864K), 0.0094315 secs]
[GC (Allocation Failure)  392283K->366802K(402444K), 0.0086878 secs]
[GC (Allocation Failure)  401670K->379674K(415352K), 0.0106495 secs]
[GC (Allocation Failure)  414373K->393463K(429036K), 0.0112755 secs]
[GC (Allocation Failure)  427991K->405551K(441076K), 0.0112701 secs]
[GC (Allocation Failure)  440253K->416277K(451968K), 0.0094576 secs]
[GC (Allocation Failure)  451215K->425780K(461468K), 0.0090022 secs]
[GC (Allocation Failure)  460290K->436772K(472444K), 0.0094107 secs]
[GC (Allocation Failure)  471716K->446455K(482156K), 0.0082263 secs]
[GC (Allocation Failure)  481367K->458277K(494016K), 0.0108484 secs]
[GC (Allocation Failure)  493221K->472866K(508428K), 0.0136895 secs]
[GC (Allocation Failure)  507531K->482224K(517900K), 0.0089288 secs]
[GC (Allocation Failure)  516722K->496630K(532228K), 0.0138994 secs]
[GC (Allocation Failure)  530929K->510278K(545916K), 0.0122755 secs]
[GC (Allocation Failure)  545222K->525169K(560748K), 0.0115490 secs]
[GC (Allocation Failure)  560103K->538917K(574508K), 0.0101969 secs]
[GC (Allocation Failure)  573758K->549968K(585604K), 0.0108499 secs]
[GC (Allocation Failure)  584621K->561502K(597140K), 0.0105879 secs]
[GC (Allocation Failure)  596328K->574737K(610396K), 0.0115948 secs]
[GC (Allocation Failure)  609564K->586209K(621888K), 0.0103855 secs]
[GC (Allocation Failure)  620848K->598905K(634548K), 0.0114484 secs]
[GC (Allocation Failure)  633849K->609954K(645672K), 0.0104366 secs]
[GC (Allocation Failure)  644581K->619651K(655396K), 0.0093347 secs]
[GC (Allocation Failure)  654499K->628112K(663832K), 0.0068532 secs]
[GC (Allocation Failure)  663056K->642514K(678284K), 0.0129166 secs]
[GC (Allocation Failure)  677274K->657114K(692704K), 0.0126130 secs]
[GC (Allocation Failure)  692058K->670309K(706092K), 0.0121167 secs]
[GC (Allocation Failure)  704882K->683000K(718772K), 0.0109829 secs]
over!create object count number is:7525
```

young GC频繁，但是平均失效较小，Full GC 几乎没有，从数据来看不如同期并行GC

##### 实验三

```properties
java -Xms128m -Xmx2g -XX:+UseConcMarkSweepGC -XX:+PrintGC GCLogAnalysis
... ... 
over!create object count number is:7966
```

此次测试结果与实验二类似

###### 实验四

```properties
java -Xms128m -Xmx6g -XX:+UseConcMarkSweepGC -XX:+PrintGC GCLogAnalysis
... ... 
over!create object count number is:7126
```

此次测试与实验二类似

###### 分析

总体来看，CMS在测试数据表现上不如 并行GC

#### G1

###### 测试一

```properties
java -Xms128m -Xmx512m -XX:+UseG1GC -XX:+PrintGC GCLogAnalysis
# 结果
[GC pause (G1 Evacuation Pause) (young) 7953K->3914K(128M), 0.0017039 secs]
[GC pause (G1 Evacuation Pause) (young) 16M->10M(128M), 0.0031549 secs]
[GC pause (G1 Evacuation Pause) (young) 35M->19M(128M), 0.0022388 secs]
[GC pause (G1 Evacuation Pause) (young) 56M->30M(128M), 0.0041181 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 106M->53M(142M), 0.0050840 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0007730 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0026919 secs]
[GC remark, 0.0016312 secs]
[GC cleanup 71M->71M(142M), 0.0024819 secs]
[GC pause (G1 Evacuation Pause) (young) 109M->71M(146M), 0.0042012 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 72M->71M(146M), 0.0026757 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0012048 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0032586 secs]
[GC remark, 0.0034634 secs]
[GC cleanup 91M->91M(146M), 0.0005101 secs]
[GC pause (G1 Evacuation Pause) (young) 114M->87M(146M), 0.0030562 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 91M->89M(146M), 0.0023139 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0009643 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0042700 secs]
[GC remark, 0.0013581 secs]
[GC cleanup 115M->115M(146M), 0.0004626 secs]
[GC pause (G1 Evacuation Pause) (young) 116M->99M(146M), 0.0024388 secs]
[GC pause (G1 Evacuation Pause) (mixed) 104M->99M(146M), 0.0014338 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 100M->99M(220M), 0.0032127 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0007518 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0038152 secs]
[GC remark, 0.0042601 secs]
[GC cleanup 109M->109M(220M), 0.0008560 secs]
[GC pause (G1 Evacuation Pause) (young) 182M->126M(288M), 0.0073570 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 129M->128M(333M), 0.0046130 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0007118 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0036257 secs]
[GC remark, 0.0016238 secs]
[GC cleanup 139M->139M(333M), 0.0014060 secs]
[GC pause (G1 Evacuation Pause) (young) 250M->166M(369M), 0.0062868 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 167M->167M(398M), 0.0037264 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0034493 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0041113 secs]
[GC remark, 0.0021478 secs]
[GC cleanup 180M->180M(398M), 0.0004400 secs]
[GC pause (G1 Evacuation Pause) (young) 323M->214M(434M), 0.0069979 secs]
[GC pause (G1 Evacuation Pause) (mixed) 222M->215M(450M), 0.0032190 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 218M->216M(463M), 0.0019172 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0009896 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0037369 secs]
[GC remark, 0.0018219 secs]
[GC cleanup 228M->227M(463M), 0.0014511 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0008061 secs]
[GC pause (G1 Evacuation Pause) (young) 367M->255M(473M), 0.0050998 secs]
[GC pause (G1 Evacuation Pause) (mixed) 265M->238M(481M), 0.0042031 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 241M->239M(488M), 0.0016166 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0010861 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0040510 secs]
[GC remark, 0.0021575 secs]
[GC cleanup 254M->253M(488M), 0.0028734 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0012110 secs]
[GC pause (G1 Evacuation Pause) (young) 389M->285M(494M), 0.0058215 secs]
[GC pause (G1 Evacuation Pause) (mixed) 296M->273M(498M), 0.0040851 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 274M->274M(501M), 0.0015062 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0017952 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0051879 secs]
[GC remark, 0.0020471 secs]
[GC cleanup 304M->302M(501M), 0.0018170 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0006149 secs]
[GC pause (G1 Evacuation Pause) (young) 407M->312M(507M), 0.0051511 secs]
[GC pause (G1 Evacuation Pause) (mixed) 328M->297M(508M), 0.0075079 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 299M->297M(509M), 0.0020520 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0007812 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0045134 secs]
[GC remark, 0.0065527 secs]
[GC cleanup 331M->331M(509M), 0.0009391 secs]
[GC pause (G1 Evacuation Pause) (young) 402M->326M(510M), 0.0049598 secs]
[GC pause (G1 Evacuation Pause) (mixed) 345M->309M(511M), 0.0051742 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 320M->310M(512M), 0.0018864 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0084576 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0088185 secs]
[GC remark, 0.0019697 secs]
[GC cleanup 389M->389M(512M), 0.0017135 secs]
[GC pause (G1 Evacuation Pause) (young) 418M->340M(512M), 0.0048423 secs]
[GC pause (G1 Evacuation Pause) (mixed) 355M->321M(512M), 0.0050771 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 322M->321M(512M), 0.0014793 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0015664 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0089179 secs]
[GC remark, 0.0021676 secs]
[GC cleanup 377M->377M(512M), 0.0011725 secs]
[GC pause (G1 Evacuation Pause) (young) 404M->341M(512M), 0.0033200 secs]
[GC pause (G1 Evacuation Pause) (mixed) 358M->326M(512M), 0.0069535 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 328M->326M(512M), 0.0013787 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0008132 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0050692 secs]
[GC remark, 0.0045980 secs]
[GC cleanup 371M->371M(512M), 0.0008692 secs]
[GC pause (G1 Evacuation Pause) (young) 403M->344M(512M), 0.0052802 secs]
[GC pause (G1 Evacuation Pause) (mixed) 365M->332M(512M), 0.0048703 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 332M->332M(512M), 0.0040400 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0003863 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0051279 secs]
[GC remark, 0.0021065 secs]
[GC cleanup 362M->362M(512M), 0.0010521 secs]
[GC pause (G1 Evacuation Pause) (young) 413M->353M(512M), 0.0040554 secs]
[GC pause (G1 Evacuation Pause) (mixed) 372M->339M(512M), 0.0077118 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 341M->339M(512M), 0.0018902 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0012673 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0053771 secs]
[GC remark, 0.0051123 secs]
[GC cleanup 378M->378M(512M), 0.0008832 secs]
[GC pause (G1 Evacuation Pause) (young) 393M->354M(512M), 0.0032337 secs]
[GC pause (G1 Evacuation Pause) (mixed) 376M->340M(512M), 0.0069688 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 346M->341M(512M), 0.0018923 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0019835 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young) 397M->357M(512M), 0.0034796 secs]
[GC concurrent-mark-end, 0.0156810 secs]
[GC remark, 0.0077641 secs]
[GC cleanup 361M->361M(512M), 0.0009687 secs]
[GC pause (G1 Evacuation Pause) (young) 403M->370M(512M), 0.0044100 secs]
[GC pause (G1 Evacuation Pause) (mixed) 393M->361M(512M), 0.0043397 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 362M->361M(512M), 0.0054450 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0006878 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young) 398M->370M(512M), 0.0028492 secs]
[GC concurrent-mark-end, 0.0088210 secs]
over!create object count number is:7145
[GC remark, 0.0026217 secs]
[GC cleanup 378M->378M(512M), 0.0023054 secs]
```

从测试数据来看，gc过于频繁，但单次GC时长较小，时长较为平均，但数据结果不如并行

###### 实验二

```properties
 java -Xms128m -Xmx1g -XX:+UseG1GC -XX:+PrintGC GCLogAnalysis
# 结果
[GC pause (G1 Evacuation Pause) (young) 9145K->3400K(128M), 0.0022076 secs]
[GC pause (G1 Evacuation Pause) (young) 18M->10M(128M), 0.0032606 secs]
[GC pause (G1 Evacuation Pause) (young) 32M->19M(128M), 0.0032630 secs]
[GC pause (G1 Evacuation Pause) (young) 53M->31M(128M), 0.0033034 secs]
[GC pause (G1 Evacuation Pause) (young) 90M->50M(128M), 0.0045008 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 57M->51M(128M), 0.0038055 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0014077 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0020215 secs]
[GC remark, 0.0016902 secs]
[GC cleanup 78M->78M(128M), 0.0015159 secs]
[GC pause (G1 Evacuation Pause) (young) 102M->73M(135M), 0.0036297 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 75M->74M(135M), 0.0015570 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0014161 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0031784 secs]
[GC pause (G1 Evacuation Pause) (young) 106M->86M(135M), 0.0035626 secs]
[GC remark, 0.0015104 secs]
[GC cleanup 86M->86M(135M), 0.0014639 secs]
[GC pause (G1 Evacuation Pause) (young) 104M->92M(135M), 0.0020180 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 94M->92M(135M), 0.0011793 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0016937 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young) 103M->95M(270M), 0.0050917 secs]
[GC concurrent-mark-end, 0.0079892 secs]
[GC remark, 0.0012995 secs]
[GC cleanup 99M->99M(270M), 0.0017840 secs]
[GC pause (G1 Evacuation Pause) (young) 225M->130M(437M), 0.0124984 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 314M->186M(555M), 0.0101927 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0011573 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0037466 secs]
[GC remark, 0.0021563 secs]
[GC cleanup 193M->193M(555M), 0.0014230 secs]
[GC pause (G1 Evacuation Pause) (young) 418M->246M(649M), 0.0131383 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 382M->279M(724M), 0.0086363 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0012556 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0046845 secs]
[GC remark, 0.0021162 secs]
[GC cleanup 291M->291M(724M), 0.0018303 secs]
[GC pause (G1 Evacuation Pause) (young) 627M->368M(848M), 0.0270850 secs]
[GC pause (G1 Evacuation Pause) (mixed) 375M->349M(884M), 0.0067748 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 366M->352M(912M), 0.0032696 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0007878 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0072526 secs]
[GC remark, 0.0029619 secs]
[GC cleanup 369M->368M(912M), 0.0012732 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0019658 secs]
[GC pause (G1 Evacuation Pause) (young) 735M->441M(936M), 0.0110021 secs]
[GC pause (G1 Evacuation Pause) (mixed) 453M->400M(954M), 0.0073911 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 401M->400M(968M), 0.0052869 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0007217 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0057294 secs]
[GC remark, 0.0022240 secs]
[GC cleanup 410M->409M(968M), 0.0011135 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0009031 secs]
over!create object count number is:6228
```

GC频率下降了，但多次测试，效果确实不如并行GC，

###### 实验三

```properties
java -Xms128m -Xmx2g -XX:+UseG1GC -XX:+PrintGC GCLogAnalysis
# 结果
[GC pause (G1 Evacuation Pause) (young) 8239K->2904K(128M), 0.0016326 secs]
[GC pause (G1 Evacuation Pause) (young) 14M->5003K(128M), 0.0024383 secs]
[GC pause (G1 Evacuation Pause) (young) 97M->35M(132M), 0.0051422 secs]
[GC pause (G1 Evacuation Pause) (young) 68M->46M(132M), 0.0027036 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 78M->58M(132M), 0.0025128 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0041351 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0042776 secs]
[GC remark, 0.0016201 secs]
[GC cleanup 98M->98M(132M), 0.0021769 secs]
[GC pause (G1 Evacuation Pause) (young) 100M->69M(132M), 0.0036118 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 69M->69M(132M), 0.0022424 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0010716 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0025640 secs]
[GC remark, 0.0018602 secs]
[GC cleanup 88M->88M(132M), 0.0009160 secs]
[GC pause (G1 Evacuation Pause) (young) 103M->81M(132M), 0.0049564 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 85M->82M(132M), 0.0016553 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001645 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0025094 secs]
[GC remark, 0.0045384 secs]
[GC cleanup 92M->92M(132M), 0.0013856 secs]
[GC pause (G1 Evacuation Pause) (young) 99M->87M(132M), 0.0015235 secs]
[GC pause (G1 Evacuation Pause) (mixed) 91M->86M(132M), 0.0018478 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 87M->87M(264M), 0.0051846 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0007802 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0028747 secs]
[GC remark, 0.0018761 secs]
[GC cleanup 97M->97M(264M), 0.0009110 secs]
[GC pause (G1 Evacuation Pause) (young) 213M->130M(552M), 0.0174160 secs]
[GC pause (G1 Evacuation Pause) (young) 445M->208M(875M), 0.0247064 secs]
[GC pause (G1 Evacuation Pause) (young) 705M->327M(1142M), 0.0269431 secs]
over!create object count number is:5321
```

堆内存增大了，GC频率变小了，但单次GC时长明显提升，效率下降明显

###### 测试四

```properties
 java -Xms128m -Xmx6g -XX:+UseG1GC -XX:+PrintGC GCLogAnalysis
  ... ...
 over!create object count number is:4704
```

无论多么不可思议，再次验证实验三的趋势，表现下降明显

###### 分析

G1是GDK新出的GC，表现应该比老GC要好，但在GDK8中表现却并不如意，猜测可能不同版本GDK对不同的GC不一样，新版本会对GC做些优化

#### gateway-server-0.0.1-SNAPSHOT.jar测试

###### 测试一

1,启动gateway-server-0.0.1-SNAPSHOT.jar

```vm
java -jar -Xmx152m -XX:NewRatio=2 .\gateway-server-0.0.1-SNAPSHOT.jar
```

2 ,测压

```properties
PS  sb -u http://127.0.0.1:8088/api/hello -B specified -N60

20552   (RPS: 287)
Status 200:    20552
RPS: 336.2 (requests/second)
Max: 104ms
Min: 1ms
Avg: 1.2ms
  50%   below 1ms
  60%   below 1ms
  70%   below 1ms
  80%   below 1ms
  90%   below 2ms
  95%   below 2ms
  98%   below 3ms
  99%   below 3ms
99.9%   below 5ms
```

3,gc情况

```properties
S0C    S1C    S0U    S1U      EC       EU        OC         
0.0   1024.0  0.0    10.1  13312.0   1024.0   26624.0   
OU         MC       MU    CCSC   CCSU      YGC     YGCT  FGC
18237.5   36528.0 35803.0 4352.0 4073.2    253    0.759   0     
FGCT    CGC    CGCT     GCT
0.000   8      0.033    0.792
```

###### 测试二

1,启动gateway-server-0.0.1-SNAPSHOT.jar

```properties
java -jar -Xmx25m -XX:NewRatio=2 .\gateway-server-0.0.1-SNAPSHOT.jar
```

2 ,测压

```properties
PS  sb -u http://127.0.0.1:8088/api/hello -B specified -N60
18309   (RPS: 255)
Status 200:    18309
RPS: 299.4 (requests/second)
Max: 611ms
Min: 1ms
Avg: 1.6ms
  50%   below 1ms
  60%   below 2ms
  70%   below 2ms
  80%   below 2ms
  90%   below 3ms
  95%   below 3ms
  98%   below 4ms
  99%   below 5ms
99.9%   below 8ms
```

3,gc情况

```properties
 S0C    S1C    S0U    S1U      EC       EU
 0.0   1024.0  0.0    18.4   8192.0   5120.0  
  OC         OU       MC      MU      CCSC   CCSU     YGC    
 17408.0    14657.9   35632.0 34867.2 4224.0 4011.7    119    
  YGCT    FGC    FGCT    CGC    CGCT     GCT
 0.428   3      0.224  84      0.243    0.895
```

###### 测试三

1,启动gateway-server-0.0.1-SNAPSHOT.jar

```properties
java -jar -Xmx1g -XX:NewRatio=2 .\gateway-server-0.0.1-SNAPSHOT.jar
```

2 ,测压

```properties
PS sb -u http://127.0.0.1:8088/api/hello -B specified -N60

20485   (RPS: 286.2)
Status 200:    20485
RPS: 335.1 (requests/second)
Max: 84ms
Min: 1ms
Avg: 1.1ms
  50%   below 1ms
  60%   below 1ms
  70%   below 1ms
  80%   below 1ms
  90%   below 1ms
  95%   below 2ms
  98%   below 2ms
  99%   below 3ms
99.9%   below 4ms
```

3,gc情况

```properties
 S0C    S1C    S0U    S1U      EC       EU       
 0.0   1024.0  0.0    10.1  19456.0   6144.0 
  OC         OU        MC      MU     CCSC    CCSU    YGC     
 38912.0    22435.5   35632.0 35027.9 4224.0 4038.1     70   
  YGCT    FGC    FGCT    CGC    CGCT     GCT
 0.347   0      0.000   2      0.012    0.359
```



```
经三次测试数据来看（实际测试多次，取典型），随着堆内存增加，gc时间减少，请求处理数增加，但到一定程度达到瓶颈无法增加，测试过程中也出现一场数据，在较低内存分配情况下请求处理数到达2000，在此记录，
```



### 结论

使用不同的jvm参数启动GCLogAnalysis.java，观察不同不同jvm的执行情况：

- 相同堆内存下各个gc执行占用时间顺序为：G1<CMS<并行<串行（偶尔会有意外，总体来说是这样）
  - 在本次测试中无论多么不情愿，并行是表现最好的，可能是用GDK8的缘故，G1不够完善，并行是8的默认
- 相同GC下，最大堆内存逐渐增加，性能成倒U型曲线，内存无限大不在考虑范围内
  - 年轻代逐渐增大，younggc频率成下降趋势，但fullgc可能会增加
  - 年轻代和老年代的比例要根据应用程序实际偏向调整比例

使用测压工具sb对gateway-server-0.0.1-SNAPSHOT.jar在不同的jvm参数下的执行情况，分析最合适的堆大小设置，以支持程序在高并发的情况下性能（处理的请求数量）最好。

- 随着堆内存增加，gc时间减少，请求处理数增加，
- 堆内存增加到一定数量，请求数不在增加

JVM参数调整在不同的系统和环境中可能差异很大，需要多次实验和摸索，没有一陈不变的固定，只有随机应变的灵活