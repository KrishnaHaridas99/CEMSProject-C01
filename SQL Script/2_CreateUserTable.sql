USE [CEMS]
GO

/****** Object:  Table [dbo].[employee]    Script Date: 16-10-2023 01:46:22 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CEMS_Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [varchar](40) NOT NULL,
	[LastName] [varchar](40) NOT NULL,
	[DOB] [date] NULL,
	[Email] [varchar](30) NULL,
	[Phone_no] [varchar](30) NULL,
	[UserType] [int],
	[UserName] [varchar](10) NOT NULL,
	[Password] [varchar](40) NOT NULL
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CEMS_Users]  WITH CHECK ADD FOREIGN KEY([UserType])
REFERENCES [dbo].[CEMS_UserType] ([UserTypeID])
GO


