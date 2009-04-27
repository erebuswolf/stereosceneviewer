% file SceneViewer.m
% function SceneViewer
%
% This file demonstrates the use of the SceneViewer application
% Author: Jesse Fish

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
controller.setBufferCommands(false);
display('created controller');

%run a short demo
%load the demo scene
controller.loadScene('/Users/jesse/programs/BMEProject/3dSceneViewer/data/Scene1.xml');

%make things move around in the demo scene

display('scene loading');


pause(1);
controller.setObjectScale('pound2', 1, 2, 1);

pause(1);
controller.setObjectTransparency('pound2', 0.5);

pause(1);
controller.setObjectColorDiffuse('pound2', 1, 0, 0);
controller.setObjectColorAmbient('pound2', 1, 0, 0);
controller.setObjectColorSpecular('pound2', 1, 0, 0);

pause(1);

controller.setCameraUpVector(1, 1, 0);

pause(1);

controller.setCameraPosition(0, 100, 1);

pause(1);

controller.setCameraTarget(1, 0, 1);

pause(1);

controller.setCameraPosition(0, 0, 100);
controller.setCameraTarget(0,0,0);
controller.setCameraUpVector(0, 1, 0);

pause(1);

controller.setObjectRotation('pound2',0,0,1,0);
controller.setObjectRotation('pound1',0,0,1,0);

pause(1);

controller.setCameraFovNF(120,1,300)
pause(1);

controller.setCameraFovNF(45,.1,500)
pause(1);

controller.setCameraIOD(5);
pause(1);

controller.setCameraIOD(1);
pause(1);

controller.setClearColor(.5,.5,0,1);
pause(1);

controller.setClearColor(0,0,0,1)
pause(1);

controller.setGlobalLightValues(1,1,1,1);
pause(1);

controller.setGlobalLightValues(0,0,0,1);
pause(1);

controller.setLightOn('light1',false);
pause(1);

controller.setLightOn('light2',false);
pause(1);
controller.setLightOn('light3',false);
pause(1);

controller.setLightOn('light2',true);
pause(1)
p=0
while(p<100)
    controller.setLightPosition('light2',3-6*(p/100),0,1);
    p=p+1;
    pause(.05);
end
controller.setObjectDraw('pound1',false)
pause(1)

controller.setObjectDraw('pound1',true)
controller.setObjectDraw('pound2',false)
pause(1)
controller.setObjectDraw('pound2',true)

% 
% controller.quit();
% controller.flushCommands();
% %close the controller when you are done with the program
% controller.close();
