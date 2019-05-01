IF EXISTS (
SELECT *
FROM sys.objects
WHERE object_id = OBJECT_ID(N'[dbo].[psp_transaction]') AND type in (N'P', N'PC')) DROP PROCEDURE [dbo].[psp_transaction]
GO

CREATE PROCEDURE [dbo].[psp_transaction]
(
@id [bigint] = NULL OUTPUT,
@filename [varchar](255),
@initiator [varchar](255),
@date_converted [datetime],
@total_transaction [varchar](255)
)

AS BEGIN
	SET NOCOUNT ON
	DECLARE @Err int

	INSERT INTO [dbo].[tbl_Transactions]
	(
	filename,
	initiator,
	date_converted,
	total_transaction
	)
	VALUES
	(
	@filename,
	@initiator,
	@date_converted,
	@total_transaction
	)
	SET @Err = @@Error
	SELECT @id = SCOPE_IDENTITY()

	RETURN @Err
END
GO

IF EXISTS (
SELECT *
FROM sys.objects
WHERE object_id = OBJECT_ID(N'[dbo].[psp_transactions_summary]') AND type in (N'P', N'PC')) DROP PROCEDURE [dbo].[psp_transactions_summary]
GO

CREATE PROCEDURE [dbo].[psp_transaction_summary]
(
@id [bigint] = NULL OUTPUT,
@transaction_type [varchar](255),
@transaction_amount [varchar](255),
@transaction_location [varchar](255)
)

AS BEGIN
	SET NOCOUNT ON
	DECLARE @Err int

	INSERT INTO [dbo].[tbl_transactions_summary]
	(
	transaction_type,
	transaction_amount,
	transaction_location
	)
	VALUES
	(
	@transaction_type,
	@transaction_amount,
	@transaction_location
	)
	SET @Err = @@Error
	SELECT @id = SCOPE_IDENTITY()

	RETURN @Err
END
GO