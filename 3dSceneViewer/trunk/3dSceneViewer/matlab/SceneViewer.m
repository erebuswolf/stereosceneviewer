% file SceneViewer.m
% function SceneViewer
% args:
% port: the port number the controller and SceneViewer communicate on
% returns: the controller object to control the SceneViewer
% creates and returns a controller object
% Author: Jesse Fish
function [controller]=SceneViewer(port)
controller= com.scenecontrol.Controller(port);
end
