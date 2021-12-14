# ICSI404 HW3 ReadMe

For the testing I just write all the memory data inside the preload() method.  Because the doc. did not say anything about how to use the preload(String[]) method, I just write all the code in this method.



what I do is I write is :

R0 = 10   (R0 as register)

R1 = -1

R2 = 6

R3 = R1 + R0

(Show the register list)

R3 = R1 + R3

If(R3 != R2)

then jump to R3 = R1 + R3

Jump 30 line

30 > R4 = R3 - R1



## Preload(in code)

0 > MOV R1 -1

2 > MOV R0 10

4 > MOV R2 6

6 > Add R3 R1 R0

8 > INT 0

10 > Add R3 R1 R3

12 > INT 0

14 > CMP R3 R2

16 > BNE 10

18 > INT 0

20 > JMP 30

22 > HLT

*

30 > SUB R4 R3 R1

32 > INT 0

34 > HLT

---END---





## Preload(in binary)

0 > 0000 0000 0000 0000 0010 0001 1111 1111 

2 > 0000 0000 0000 0000 0010 0000 0000 1010 

4 > 0000 0000 0000 0000 0010 0010 0000 0110

6 > 0000 0000 0000 0000 1011 0011 0001 0000

8 > 0000 0000 0000 0000 0001 0000 0000 0000

10 > 0000 0000 0000 0000 1011 0011 0001 0011

12 > 0000 0000 0000 0000 0001 0000 0000 0000

14 > 0000 0000 0000 0000 0100 0000 0011 0010

16 > 0000 0000 0000 0000 0101 0000 0000 0101

18 > 0000 0000 0000 0000 0001 0000 0000 0000

20 > 0000 0000 0000 0000 0011 0000 0000 1111

22 > 0000 0000 0000 0000 0000 0000 0000 0000

*

30 > 0000 0000 0000 0000 1100 0100 0011 0001

32 > 0000 0000 0000 0000 0001 0000 0000 0000

34 > 0000 0000 0000 0000 0000 0000 0000 0000



---END---