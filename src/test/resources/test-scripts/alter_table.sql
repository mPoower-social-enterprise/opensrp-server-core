ALTER TABLE core.client ADD COLUMN IF NOT EXISTS base_entity_id varchar NULL DEFAULT ''::character varying;
ALTER TABLE core.client ADD COLUMN IF NOT EXISTS division varchar NULL DEFAULT ''::character varying;
ALTER TABLE core.client ADD COLUMN IF NOT EXISTS branch varchar NULL DEFAULT ''::character varying;
ALTER TABLE core.client ADD COLUMN IF NOT EXISTS village varchar NULL DEFAULT ''::character varying;
ALTER TABLE core.client ADD COLUMN IF NOT EXISTS district varchar NOT NULL DEFAULT ''::character varying;


ALTER TABLE core.client_metadata ADD COLUMN IF NOT EXISTS address1 varchar NULL;
ALTER TABLE core.client_metadata ADD COLUMN IF NOT EXISTS address2 varchar NULL;
ALTER TABLE core.client_metadata ADD COLUMN IF NOT EXISTS address3 varchar NULL;
ALTER TABLE core.client_metadata ADD COLUMN IF NOT EXISTS village_id int8 NULL;
ALTER TABLE core.client_metadata ADD COLUMN IF NOT EXISTS district varchar NOT NULL DEFAULT ''::character varying;
ALTER TABLE core.client_metadata ADD COLUMN IF NOT EXISTS division varchar NULL;
ALTER TABLE core.client_metadata ADD COLUMN IF NOT EXISTS branch varchar NULL;



ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS base_entity_id varchar NULL DEFAULT ''::character varying;
ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS district varchar NOT NULL DEFAULT ''::character varying;
ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS division varchar NULL DEFAULT ''::character varying;
ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS branch varchar NULL DEFAULT ''::character varying;
ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS village varchar NULL DEFAULT ''::character varying;
ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS provider_id varchar NULL DEFAULT ''::character varying;
ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS event_type varchar NULL DEFAULT ''::character varying;
ALTER TABLE core."event" ADD COLUMN IF NOT EXISTS form_submission_id varchar NULL DEFAULT ''::character varying;


ALTER TABLE core.event_metadata ADD COLUMN IF NOT EXISTS district varchar NOT NULL DEFAULT ''::character varying;
ALTER TABLE core.event_metadata ADD COLUMN IF NOT EXISTS division varchar NULL DEFAULT ''::character varying;
ALTER TABLE core.event_metadata ADD COLUMN IF NOT EXISTS branch varchar NULL DEFAULT ''::character varying;
ALTER TABLE core.event_metadata ADD COLUMN IF NOT EXISTS village varchar NULL DEFAULT ''::character varying;

