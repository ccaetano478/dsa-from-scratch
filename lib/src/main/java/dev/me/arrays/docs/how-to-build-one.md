## How to build an actual array from scratch?

Let me start by saying you cannot ! At least in Java. 

I'll show you why: An array is a **contiguous block of memory** with a fixed size determined at creation time. 
In Java, when you do ````new int[5]```` the JVM allocates exactly enough memory for 5 integers side by side. 
But you are creating an array from scratch so one of the things you should be able to do is to allocate you own contiguous block of memory right?

NOOOO rs.

In Java the JVM is the only one allowed to allocate memory space. You just tell it that you want it... It decides where, how and when. 

So for our little experiment here we are actually just creating an interface and the behavior of an array, not reinventing memory allocation.

First things first we need to understand **What do we actually need?** 
- Since an array is not resizable, and we actually need to determine the chunk of me memory we want to allocate in creation time we need to determine the ````capacity```` of the array. 
   1. This is  going to be an ````Integer```` 
 - We'll also need the ````size```` that it's going to act like a pointer to the last slot of memory used
   1. This is also going to be an ````Integer````
 - Another thing we need is the actual data the goes into the slots of the array
    1. This is going to be an array of Objects - ``Objects[]``
 - Then we need the methods to mess with the array
   1. ```get(int index) ```- should receive an index and return the object in the index
   2. ```push(Object item) ```- take in an item, and we add this item to end or being the length/size of the array
   3. ```pop()``` - remove and return the last inserted item.



