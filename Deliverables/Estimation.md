1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
# Project Estimation  
Authors: Alberto Cipollina, Florin Gheorghiu, Simone de Stefano
Date: 30/04/2021
Version: 1
# Contents
- [Estimate by product decomposition](#Estimate-by-product-decomposition)
- [Estimate by activity decomposition](#Estimate-by-activity-decomposition)
# Estimation approach
# Estimate by product decomposition
### 
Estimates by product decomposition DO NOT include project activities, just the overall coding activities.

|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed  |             150            |             
| A = Estimated average size per class, in LOC       |             80               | 
| S = Estimated size of project, in LOC (= NC * A) | 12000 |
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  |                  1200                  |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 36000 | 
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) |        7.5           |               
# Estimate by activity decomposition
###
Activities are done by a team of 4 people, working 8 hours a day, 5 days per week. Some activities can be done entirely or partially in parallel.

|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- | 
| ***Planning***     | 480  |
|   Requirements     | 160  |
|   GUI Prototype    | 96   |
|   Design           | 160  |   
|   Estimation       | 64   |
| ***Coding***       | 1200 |
|   FR Coding        | 640  |
|   Interface Coding | 640  |
|   FR+NFR Testing   | 320  |
|   Final Testing    | 256  |
| ***Deployment***   | 160  |

### Gantt 

```plantuml
@startuml Gantt
[Planning] lasts 15 days

[Requirements] lasts 5 days

[GUI Prototype] lasts 3 days
[GUI Prototype] starts at [Requirements]'s end

[Design] lasts 5 days
[Design] starts at [GUI Prototype]'s end

[Estimation] lasts 2 days
[Estimation] starts at [Design]'s end

[Coding] lasts 38 days
[Coding] starts at [Planning]'s end
[Coding] starts at [Estimation]'s end

[FR Coding] lasts 20 days
[FR Coding] starts at [Coding]'s start

[Interface Coding] lasts 20 days
[Interface Coding] starts 5 days after [FR Coding]'s start

[FR+NFR Testing] lasts 10 days
[FR+NFR Testing] starts at [FR Coding]'s end

[Final Testing] lasts 8 days
[Final Testing] starts at [FR+NFR Testing]'s end

[Deployment] lasts 5 days
[Deployment] starts at [Coding]'s end
[Deployment] starts at [Final Testing]'s end
@enduml
```
