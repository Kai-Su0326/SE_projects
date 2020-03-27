# SE_Projects
## Java testing framework
### Introduction
This project includes multiple Java testing framework components and I extended it to support random test case generation. To access the class and annotation of methods' return value and input arguments, java.lang.reflcect was pervasively used in the project. 
<br>
<br>
I implemented method testClass():<br>
<pre><code>public static HashMap<String, Throwable> testClass(String name);</code></pre>
Given a class name, this method should run all the test cases in that class. The return value is a map where the keys of the map are the test case names, and the values are either the exception or error thrown by a test case (indicating that test case failed) or null for test cases that passed. 
<br>
The test execution engine invoke the methods with different annotaion in following sequence(like JUnit):
+ All methods annotated as @BeforeClass, they would be executed once before any tests in the class are run. If there are multiple @BeforeClass methods, they are executed in alphabetical order.
+ All methods annotated as @Before, they would be invoked before each execution of a test method. Multiple @Before methods would also be run in alphabetical order.
+ Methods annotated @AfterClass and @After are analogous to @BeforeClass and @Before, except they would be run after the test methods.
<br>
<br>
The method quickCheckClass() would generate all of the possible test cases within given range in parameters' annotations, which is:
<pre><code>public static HashMap<String, Object[]> quickCheckClass(String name);</code></pre>
Here is an example of how this method works:
<pre><code>
@Property
boolean absNonNeg(@IntRange(min=-10, max=10) Integer i) {
  return Math.abs(i.intValue()) >= 0;
}
</code></pre>
Then, quickCheckClass() would call absNonNeg with many different input integers ranging from -10 to 10, inclusive. For the first one for which absNonNeg returns false, quickCheckClass would add a mapping from the method name ("absNonNeg") to the array of arguments for which the method returned false or threw a Throwable. Otherwise, if the property runs until termination with only true return values, the "absNonNeg" would be mapped to null. Then quickCheckClass will run the next property in the class.
<br>
<br>
Except the arguments of Int, quickCheckClass also checks annotated arguments of String(@StringSet) and List(@ListLength). 
List arguments would be annotated with @ListLength(min=i1, max=i2), indicating the minimum and maximum (inclusive) list lengths for the argument. The tricky part was handling the list of list part(eg:<code> @ListLength(min=0, max=2) List<@ListLength(min=0,max=3) List<@IntRange(min=5, max=7) Integer>></code>) and I used recursive helper to give all of the permutaions.
For Object arguments annotated with @ForAll(name="method", times=i), the method with the name in property's class would be called to genrate i values for the argument.

## Chess Game
This project is a good practice of multiple Java design patterns.
### Design Patterns
#### Singleton Pattern
I used singleton pattern for the board to ensure there is only one board object. In this way, the board object you created initally would carry the chess pieces information when I pass the board to other class or in case I instantiate a board again by accident. This make sure the information would not be lost. I implemented the eager instantiation version because the board class is kind of a light class.
#### Factory Pattern
I instantiate all the chess pieces with a PieceFactory which would save all chess pieces' factories in a map which would call their constructor saperately according to the input String. It would be easier to instantiate various same-leveled classes this way and people don't have to know what specific constructor should be called with Factory Pattern.
#### Observer Pattern
BoardListener class is an interface that defines BoardListener's behavior, which is implemented in Logger class. It suporvises all of the behaviors on the board including move, captures and so forth. Here it is a Logger to print out what happened on the board. I have a list of listeners as local variable in class Board, so that each behavior method can access the listeners and easily traverse them.


