SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE CEMS_SP_CreateClubMembers
	@FName varchar(100),
	@LName varchar(100),
	@DOB varchar(50),
	@Phone varchar(100),
	@Email varchar(100),
	@ClubSelected varchar(100)
AS
BEGIN

	INSERT INTO [dbo].[CEMS_Users]
		([FirstName], [LastName], [DOB], [Email], [Phone_no], [UserType], [UserName], [Password])
    VALUES
        (@FName, @LName, @DOB, @Email, @Phone, 2, @Email, '123456')

	DECLARE @MemberID INT
	SELECT @MemberID = IDENT_CURRENT('CEMS_Users')

	INSERT INTO [dbo].[CEMS_MembersClub]
		([MemberID], [ClubID], [DateAdded])
    VALUES
		(@MemberID, @ClubSelected, GETDATE())   
		
	SELECT 1 AS 'Result'
END
GO
