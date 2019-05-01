IF EXISTS (SELECT *
FROM sys.objects
WHERE object_id = OBJECT_ID(N'[dbo].[psp_find_all_transaction]') AND type in (N'P', N'PC')) DROP PROCEDURE [dbo].[psp_find_all_transaction]
GO

CREATE PROCEDURE [dbo].[psp_find_all_transaction]
    (
    @search_key varchar(255) = NULL,
    @pageNum int = NULL,
    @pageSize int = NULL
)
AS
BEGIN
    SET NOCOUNT ON
    DECLARE @recct int

    IF @pageSize IS NULL
		BEGIN
        SELECT COUNT(*) AS recct
        FROM [tbl_Transactions] (NOLOCK)
        WHERE [filename] LIKE '%'+@search_key+'%' OR
            [initiator] LIKE '%'+@search_key+'%' OR
            [date_converted] LIKE '%'+@search_key+'%' OR
            [total_transaction] = @search_key

        SELECT *
        FROM [tbl_Transactions]  (NOLOCK)
        WHERE [filename] LIKE '%'+@search_key+'%' OR
            [initiator] LIKE '%'+@search_key+'%' OR
            [date_converted] LIKE '%'+@search_key+'%' OR
            [total_transaction] = @search_key
    END
	ELSE
		BEGIN
        SELECT COUNT(*) AS recct
        FROM [tbl_Transactions] (NOLOCK)
        WHERE [filename] LIKE '%'+@search_key+'%' OR
            [initiator] LIKE '%'+@search_key+'%' OR
            [date_converted] LIKE '%'+@search_key+'%' OR
            [total_transaction] = @search_key
        DECLARE
				@lbound int,
				@ubound int

        SET @pageNum = ABS(@pageNum)
        SET @pageSize = ABS(@pageSize)

        IF @pageNum < 1 SET @pageNum = 1
        IF @pageSize < 1 SET @pageSize = 1

        SET @lbound = ((@pageNum - 1) * @pageSize)
        SET @ubound = @lbound + @pageSize + 1

        IF @lbound >= @recct
			BEGIN
            SET @ubound = @recct + 1
            SET @lbound = @ubound - (@pageSize + 1)
        END

        SELECT t2.*
        FROM (SELECT ROW_NUMBER() OVER (ORDER BY id) AS row, *
            FROM [tbl_Transactions] (NOLOCK)
            WHERE [filename] LIKE '%'+@search_key+'%' OR
                [initiator] LIKE '%'+@search_key+'%' OR
                [date_converted] LIKE '%'+@search_key+'%' OR
                [total_transaction] = @search_key) AS t2
        WHERE t2.row > @lbound AND t2.row < @ubound
    END
END
GO

CREATE PROCEDURE [find_transaction]
    (
    @search_key varchar(255) = NULL,
    @page_num int = NULL,
    @page_size int = NULL
)
AS
BEGIN
    SET NOCOUNT ON
	SELECT * FROM [dbo].[tbl_Transactions] ORDER BY id OFFSET @page_num ROWS FETCH NEXT @page_size ROWS ONLY
END

USE [discover_post_inject_web]
GO
/****** Object:  StoredProcedure [dbo].[uspGetAllMerchantUser]    Script Date: 5/1/2019 7:11:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[uspGetAllMerchantUser]
@page_num int,
@page_size int

AS
BEGIN
	SELECT [id],[filename],[initiator],[date_converted],[total_transaction]
	FROM [dbo].[tbl_Transactions]
	ORDER BY [id]
OFFSET (@page_num - 1) * @page_size ROWS
FETCH NEXT @page_size ROWS ONLY;
END
