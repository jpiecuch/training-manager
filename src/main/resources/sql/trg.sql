CREATE OR REPLACE FUNCTION TimeStampEntry() RETURNS TRIGGER AS $TimeStampEntry$
BEGIN  
  NEW.updated := current_timestamp;
  RETURN NEW;
END;
$TimeStampEntry$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS day_exercises_stamp ON day_exercises;
CREATE TRIGGER day_exercises_stamp BEFORE UPDATE ON day_exercises
    FOR EACH ROW EXECUTE PROCEDURE TimeStampEntry();