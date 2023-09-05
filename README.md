# FIT3077_project

University project for FIT3077 created in semester 1 2023.

Co-authors: Caleb Ooi, Steven Pham

## Sprint 1
- - -
Sprint 1 report can be found [here](https://docs.google.com/document/d/1R_gWm13HHV8Y4ycXj9LCcTdM1qR0Chom4dyHW19FVfI/edit#heading=h.pp13aftpgldq) or [here](./Sprint1/Sprint%201%20Deliverable.pdf)<br>
Sprint 1 domain model can be found [here](./Sprint1/Nine%20Man's%20Morris%20Domain%20Model.png) or [here](https://app.diagrams.net/#G1uehyvYCJT2Bent8LC_Y1tAOMVLp9XxV5)


## Sprint 2
- - -
Sprint 2 report can be found [here](https://git.infotech.monash.edu/fit3077-s1-2023/CL_Thursday4pm_Team38/project/-/blob/main/Sprint2/Sprint%202%20Deliverable.pdf).


## Sprint 3
- - -
Sprint 3 report can be found [here](https://git.infotech.monash.edu/fit3077-s1-2023/CL_Thursday4pm_Team38/project/-/blob/main/Sprint3/Sprint%203%20Deliverable.pdf).


## Sprint 4
- - -
Sprint 4 report can be found [here](https://git.infotech.monash.edu/fit3077-s1-2023/CL_Thursday4pm_Team38/project/-/blob/main/Sprint4/Sprint%204%20Deliverable.pdf).


## Running Game
- - -
### Download
The latest build can be found [here](https://git.infotech.monash.edu/fit3077-s1-2023/CL_Thursday4pm_Team38/project/-/tree/main/Sprint4) (requires minimum Java JDK 20).

It can be run either by using the provided .bat file (for Windows) or running the command in your command prompt inside the jar directory:
```
java -jar NineMensMorris.jar
```


### Generation
Or you can generate your own jar file. You first use the following command:
```
javac -d ./build ./Core/*.java ./Game/*.java ./UI/*.java ./PlayerPawn/*.java
```
in the src directory of the code in order to build the files.

You then change directories into the build folder
```
cd build
```
Then you run the following command
```
jar cvfe game.jar Game.Main Core/*.class Game/*.class UI/*.class ./PlayerPawn/*.class
```
in order to build the jar file. 

Otherwise one can be generated using a specific IDE such as [IntelliJ](https://www.jetbrains.com/help/idea/compiling-applications.html#package_into_jar) or [Eclipse](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-37.htm).

Afterwards the game can be run using the same command as in Download:

```
java -jar jar_name.jar
```
