
ALTER TABLE core.client DROP COLUMN IF  EXISTS base_entity_id ;
ALTER TABLE core.client DROP COLUMN IF  EXISTS division ;
ALTER TABLE core.client DROP COLUMN IF  EXISTS branch ;
ALTER TABLE core.client DROP COLUMN IF  EXISTS village ;
--ALTER TABLE core.client DROP COLUMN IF  EXISTS district ;


ALTER TABLE core.client_metadata DROP COLUMN IF  EXISTS address1 ;
ALTER TABLE core.client_metadata DROP COLUMN IF  EXISTS address2 ;
ALTER TABLE core.client_metadata DROP COLUMN IF  EXISTS address3 ;
ALTER TABLE core.client_metadata DROP COLUMN IF  EXISTS village_id ;
--ALTER TABLE core.client_metadata DROP COLUMN IF  EXISTS district ;
ALTER TABLE core.client_metadata DROP COLUMN IF  EXISTS division ;
ALTER TABLE core.client_metadata DROP COLUMN IF  EXISTS branch ;



ALTER TABLE core."event" DROP COLUMN IF  EXISTS base_entity_id ;
--ALTER TABLE core."event" DROP COLUMN IF  EXISTS district ;
ALTER TABLE core."event" DROP COLUMN IF  EXISTS division ;
ALTER TABLE core."event" DROP COLUMN IF  EXISTS branch ;
ALTER TABLE core."event" DROP COLUMN IF  EXISTS village ;
ALTER TABLE core."event" DROP COLUMN IF  EXISTS provider_id ;
ALTER TABLE core."event" DROP COLUMN IF  EXISTS event_type ;
ALTER TABLE core."event" DROP COLUMN IF  EXISTS form_submission_id ;


--ALTER TABLE core.event_metadata DROP COLUMN IF  EXISTS district ;
ALTER TABLE core.event_metadata DROP COLUMN IF  EXISTS division ;
ALTER TABLE core.event_metadata DROP COLUMN IF  EXISTS branch ;
ALTER TABLE core.event_metadata DROP COLUMN IF  EXISTS village ;
