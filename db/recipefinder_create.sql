create role recipe_admin;
alter role recipe_admin with password 'Welcome1';
alter role recipe_admin with login;
create database recipefinder owner recipe_admin;
