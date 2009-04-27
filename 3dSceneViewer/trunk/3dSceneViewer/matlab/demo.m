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
input('tested scene loading, press enter')

controller.setObjectScale('pound2', 1, 2, 1);
input('tested object scaling, press enter')


controller.setObjectTransparency('pound2', 0.5);
input('tested object transparency, press enter')

pause(1);
controller.setObjectColorDiffuse('pound2', 1, 0, 0);
controller.setObjectColorAmbient('pound2', 1, 0, 0);
controller.setObjectColorSpecular('pound2', 1, 0, 0);
input('tested changing object color, press enter')


controller.setCameraUpVector(1, 1, 0);
input('tested changing camera up vector, press enter')


controller.setCameraPosition(0, 100, 1);
input('tested changing camera position vector, press enter')

controller.setCameraTarget(1, 0,5);
input('tested changing camera target vector, press enter')

controller.setCameraPosition(0, 0, 100);
controller.setCameraTarget(0,0,0);
controller.setCameraUpVector(0, 1, 0);
input('reset camera values, press enter')

controller.setObjectRotation('pound2',0,0,1,0);
controller.setObjectRotation('pound1',0,0,1,0);
input('tested changing object rotation, press enter')

controller.setCameraFovNF(120,1,300)
input('tested changin camera field of view and near far planes, press enter')

controller.setCameraFovNF(45,.1,500)
input('reset camera field of view and near far planes, press enter')

controller.setCameraIOD(5);
input('tested changing camera Eye spread, press enter')

controller.setCameraIOD(1);
input('reset camera Eye spread, press enter')

controller.setClearColor(.5,.5,0,1);
input('tested changing world clear color, press enter')

controller.setClearColor(0,0,0,1)
input('reset changing world clear color, press enter')

controller.setGlobalLightValues(1,1,1,1);
input('testing changing global light color, press enter')

controller.setGlobalLightValues(0,0,0,1);
input('reset global light color, press enter')

controller.setLightOn('light1',false);
input('testing turning light1 off, press enter')

controller.setLightOn('light2',false);
input('testing turning light2 off, press enter')

controller.setLightOn('light3',false);
input('testing turning light3 off, press enter')

controller.setLightOn('light2',true);
input('testing turning light2 on, press enter')

display('moving light 2 from 3,0,1 to -3,0,1')
p=0;
while(p<100)
    controller.setLightPosition('light2',3-6*(p/100),0,1);
    p=p+1;
    pause(.05);
end
input('testing moving light 2 from 3,0,1 to -3,0,1, press enter')

controller.setObjectDraw('pound1',false)
input('testing turning pound1 off, press enter')

controller.setObjectDraw('pound1',true)
controller.setObjectDraw('pound2',false)
input('testing turning pound1 on and pound2 off, press enter')
controller.setObjectDraw('pound2',true)
input('testing turning pound2 on, press enter')


controller.quit();
controller.flushCommands();
%close the controller when you are done with the program
controller.close();
end
