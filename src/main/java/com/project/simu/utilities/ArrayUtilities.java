package com.project.simu.utilities;

public class ArrayUtilities {

     public static int getMinValue(int[] intArray) {
          int minValue = intArray[0];
          int minIndex = 0;
          for (int i = 0; i < intArray.length; i++) {
               if (intArray[i] < minValue) {
                    minValue = intArray[i];
                    minIndex = i;
               }
          }
          return minIndex;
     }
}