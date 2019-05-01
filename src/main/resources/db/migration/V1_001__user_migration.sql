IF NOT EXISTS (
SELECT *
FROM sys.objects
WHERE object_id = OBJECT_ID(N'[dbo].[tbl_files_data]') AND type in (N'U'))
BEGIN
    CREATE TABLE [dbo].[tbl_files_data]
    (
        [id] [bigint] IDENTITY(1,1) NOT NULL,
        [username] [varchar] (255) NOT NULL
    )
END