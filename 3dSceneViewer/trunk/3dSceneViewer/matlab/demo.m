% file SceneViewer.m
% function SceneViewer
%
% This file demonstrates the use of the SceneViewer application
% Author: Jesse Fish
function demo()
clc
clear
%this command does not work because of matlab
%!java -jar /Users/jesse/programs/BMEProject/3dSceneViewer/app/SceneViewer.jar 

%add the jar file to the java dynamic path
%this must be done before we can create the controller
javaaddpath /Users/jesse/programs/BMEProject/3dSceneViewer/app/SceneViewer.jar;
%call the method that creates the SceneController Object.
controller=SceneViewer(6789);

%run a short demo
%load the demo scene

%make things move around in the demo scene
i=0
while (i<200000)
    controller.setObjectRotation('ball1', 0, 1,0,i/10.);
	controller.setObjectRotation('ball2', 1, 0,0,i/10.);
    controller.flushCommands();
    i=i+1;
end
%close the controller when you are done with the program
controller.close();
end
