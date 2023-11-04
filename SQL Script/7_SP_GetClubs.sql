SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE CEMS_SP_GetAllClubs
AS
BEGIN
	SET NOCOUNT ON;

    SELECT ClubID, ClubName, ClubDescription, ClubPhone FROM [dbo].[CEMS_Clubs]
END
GO
