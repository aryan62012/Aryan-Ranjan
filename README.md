#About Whiteboard:-
  It is like an online drawing application where multiple user can simultaneously access the board and write or draw anything and also if any user deletes anything then it will be reflected in every board which is being used at that time.

#Protocol used-: UDP (User Datagram Protocol)

#Features provided (Everything is done using netbeans):-
  One jframe where we are putting all the textbox, jbuttons and drawpanel.
  In the Jframe, 2 Text box  are provided in which
    1.	 first textbox will accept any multicast address for host.
    2.	 second text box will accept user defined port number.
    
  Four jbutton-: 
    1.	first button one is used for joining the multicast group.
    2.	Second button is used as a pencil for drawing in the whiteboard(black color is used by default)
    3.	Third button is used as an eraser to erase the painted part.
    4.	Fourth button is used as reset button which will clear the whole screen.
    
  One drawapanel , where all the drawing would be done.
  

#Software Requirements:- jdk , NetBeans.

#Steps to run the Project:
1.	Install NetBeans.
2.	Copy the project “WhiteBoard” to it’s project folder.
3.	If you are running the project at the local host then, you have to run the project as many times as you want the no. of host.
  a)	Insert the same multicast address and port no. in each window which you have created by running it multiple times.(first text box is for IP address, second is for port number)
  b)	Click on the join button.
  c)	After joining choose the pencil button and draw.
4.	if you want to run the project at multiple host then 
  a)	Follow the first two steps.
  b)	Then enter the same multicast ip address and same port number.
  c)	Click on join button.
  d)	Start drawing by choosing the pencil button.
  e)	If you want to erase something then choose the erase button.
  f)	If you want to clear the whole drawing screen then click the clear button.




