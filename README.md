# CoreNLP for Finance

Input: "We expect that the Earnings Per Share - EPS of Google will be up by 4% in 4th quarter of 2018."

Output:

```
0	O	0	O	PRP	We	O	O	O
0	O	1	O	VBP	expect	O	O	O
0	O	2	O	IN	that	O	O	O
0	O	3	O	DT	the	O	O	O
0	FINTERM	4	O	NNS	Earnings	O	O	O
0	FINTERM	5	O	IN	Per	O	O	O
0	FINTERM	6	O	NN	Share	O	O	O
0	FINTERM	7	O	:	-	O	O	O
0	FINTERM	8	O	NN	EPS	O	O	O
0	O	9	O	IN	of	O	O	O
0	ORGANIZATION	10	O	NNP	Google	O	O	O
0	O	11	O	MD	will	O	O	O
0	O	12	O	VB	be	O	O	O
0	O	13	O	RB	up	O	O	O
0	O	14	O	IN	by	O	O	O
0	PERCENT	15	O	CD/NN	4/%	O	O	O
0	O	16	O	IN	in	O	O	O
0	DATE	17	O	JJ/NN/IN/CD	4th/quarter/of/2018	O	O	O
0	O	18	O	.	.	O	O	O
```