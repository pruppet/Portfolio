#Assignment #2, Part 2 Question 6
#28/02/2012
	.data
nums:	.word 12,45,-859,223,-725,205,402,-939,-58,101
size:	.word 10
rtnval:	.word 0
	.globl main
	.text
	
############function: findabs #######################

Takes a list of integers and returns a list with the
absolute vale of all the integers.

#####################################################

findabs:
	lw $t5, 0($t0)
	addi $t3, $zero, 0                         
	addi $t4, $zero, 0
	addi $v0, $zero, 0  		#Sets the negative counter to 0
	loop:	beq $t5, $t3, finish    
		lw $t4, 0($a0)    
      		blt $zero, $t4, skip   	
     		sub $t4, $zero, $t4	
		addi $v0, $v0, 1 
		sw $t4, 0($a0)		#Stores the absolute value in place($a0 the current index)
	skip:	addi $t3, $t3, 1        
		addi $a0, $a0, 4
      	j loop
finish:	jr $ra

main:				
	la $a0,nums
	la $t0,size
	lw $a1, 0($t0)
	jal findabs		
	la $t2,rtnval
	sw $v0, 0($t2)		#save return value in idx
print:	move $a0, $v0		#value to print goes in $a0
	li $v0, 1		#system call code for print_int
	syscall
	li $v0, 10		#exit
	syscall
	



