USE [CEMS]
GO

INSERT INTO [dbo].[CEMS_Users]
	([FirstName], [LastName], [DOB], [Email], [Phone_no], [UserType], [UserName], [Password])
VALUES
    ('Admin', 'CEMS', GETDATE(), 'admin@cems.com', '123456791', 1, 'admin', 'admin'),
	('Member', 'CEMS', GETDATE(), 'member@cems.com', '123456792', 2, 'member', 'member'),
	('Student', 'CEMS', GETDATE(), 'student@cems.com', '123456793', 3, 'Student', 'Student')
GO