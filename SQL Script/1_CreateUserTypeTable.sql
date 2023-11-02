USE [CEMS]
GO

/****** Object:  Table [dbo].[CEMS_UserType]    Script Date: 16-10-2023 01:58:11 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CEMS_UserType](
	[UserTypeID] [int] IDENTITY(1,1) NOT NULL,
	[UserType] [varchar](50) NOT NULL,
 CONSTRAINT [PK_CEMS_UserType] PRIMARY KEY CLUSTERED 
(
	[UserTypeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

