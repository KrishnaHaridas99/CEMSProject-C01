USE [CEMS]
GO

/****** Object:  Table [dbo].[CEMS_Clubs]    Script Date: 29-10-2023 07:14:10 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CEMS_Clubs](
	[ClubID] [int] IDENTITY(1,1) NOT NULL,
	[ClubName] [varchar](50) NOT NULL,
	[ClubDescription] [varchar](200) NULL,
	[ClubPhone] [varchar](50) NULL,
 CONSTRAINT [PK_CEMS_Clubs] PRIMARY KEY CLUSTERED 
(
	[ClubID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


