USE discover_post_inject_web

GO

IF NOT EXISTS ( SELECT *
              FROM sys.objects
              WHERE object_id = OBJECT_ID(N'[dbo].[tbl_Transactions]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[tbl_Transactions]
	(
		[id] [bigint] IDENTITY(1,1) NOT NULL,
		[filename] [varchar](255) NOT NULL,
		[initiator] [varchar](255) NOT NULL,
		[date_converted] [date] NOT NULL,
		[total_transaction] [varchar](255) NOT NULL
		CONSTRAINT [PK_tbl_Transaction__id] PRIMARY KEY(id)
	)
END
GO

IF NOT EXISTS ( SELECT *
              FROM sys.objects
              WHERE object_id = OBJECT_ID(N'[dbo].[tbl_Transactions_Summary]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[tbl_Transactions_Summary]
	(
		[id] [bigint] IDENTITY(1,1) NOT NULL,
		[transaction_type] [varchar](255) NOT NULL,
		[transaction_amount] [varchar](255) NOT NULL,
		[transaction_location] [varchar](255)NOT NULL
		CONSTRAINT [PK_tbl_Transaction_Summary__id] PRIMARY KEY(id)
	)
END
GO