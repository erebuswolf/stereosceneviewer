% file SceneViewer.m
% function SceneViewer
%
% This file demonstrates the use of the SceneViewer application
% Author: Jesse Fish
function hand_demo()

clc
clear
%this command does not work because of matlab
%!java -jar /Users/jesse/programs/BMEProject/3dSceneViewer/app/SceneViewer.jar

%add the jar file to the java dynamic path
%this must be done before we can create the controller
javaaddpath /Users/jesse/programs/BMEProject/3dSceneViewer/SceneViewer.jar;
%call the method that creates the SceneController Object.
controller=SceneViewer(6789);
%send commands as we call them
controller.setBufferCommands(true);
display('created controller');

%run a short demo
%load the demo scene
controller.loadScene('/Users/jesse/programs/BMEProject/3dSceneViewer/hand/handscene.xml');
controller.flushCommands();
%make things move around in the demo scene

display('scene loading');

controller.setObjectTransparency('thumb', 0.5);
controller.setObjectTransparency('fingers', 0.5);
controller.flushCommands();
steps=0;
while(steps<300000)
    controller.setObjectPosition('wrist',sin(steps*.05),sin(steps*.05),cos(steps*.05));
    controller.setObjectRotation('wrist',1,-1,1,steps);
    controller.setObjectRotation('fingers',0,0,1,steps*3+45);
    controller.setObjectRotation('thumb',0,0,1,-steps*3-45);
    
    controller.flushCommands();
    steps=steps+1;
    pause(.01)
end


controller.quit();
controller.flushCommands();
%close the controller when you are done with the program
controller.close();
end
