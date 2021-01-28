--clear data
TRUNCATE TABLE core.settings CASCADE;

ALTER SEQUENCE core.settings_id_seq RESTART WITH 15;

ALTER SEQUENCE core.settings_metadata_id_seq RESTART WITH 17;

INSERT INTO core.settings (id, json) VALUES 
(1,	'{"_id": 2, "_rev": 1, "type": "SettingConfiguration", "settings": [{"key": "pop_undernourish", "label": "Undernourished prevalence 20% or higher", "value": null, "description": "The proportion of women in the adult population (18 years or older), with a BMI less than 18.5, is 20% or higher."}, {"key": "pop_anaemia_40", "label": "Anaemia prevalence 40% or higher", "value": null, "description": "The proportion of pregnant women in the population with anaemia (haemoglobin level less than 11 g/dl) is 40% or higher."}, {"key": "pop_anaemia_20", "label": "Anaemia prevalence 20% or lower", "value": null, "description": "The proportion of pregnant women in the population with anaemia (haemoglobin level less than 11 g/dl) is 20% or lower."}, {"key": "pop_low_calcium", "label": "Low dietary calcium intake", "value": null, "description": "Women in the population are likely to have low dietary calcium intake (less than 900 mg of calcium per day)."}, {"key": "pop_tb", "label": "TB prevalence 100/100,000 or higher", "value": null, "description": "The tuberculosis prevalence in the general population is 100 cases per 100,000 persons or greater."}, {"key": "pop_vita", "label": "Vitamin A deficiency 5% or higher", "value": null, "description": "The prevalence of night blindness is 5% or higher in pregnant women or 5% or higher in children 24–59 months of age, or the proportion of pregnant women with a serum retinol level less than 0.7 mol/L is 20% or higher. "}, {"key": "pop_helminth", "label": "Soil-transmitted helminth infection prevalence 20% or higher", "value": null, "description": "The percentage of individuals in the general population infected with at least one species of soil-transmitted helminth is 20% or higher."}, {"key": "pop_hiv_incidence", "label": "HIV incidence greater than 3 per 100 person-years in the absence of PrEP", "value": null, "description": "Women in the population have a substantial risk of HIV infection. Substantial risk of HIV infection is provisionally defined as HIV incidence greater than 3 per 100 person–years in the absence of pre-exposure prophylaxis (PrEP)."}, {"key": "pop_hiv_prevalence", "label": "HIV prevalence 5% or higher", "value": null, "description": "The HIV prevalence in pregnant women in the population is 5% or higher."}, {"key": "pop_malaria", "label": "Malaria-endemic setting", "value": null, "description": "This is a malaria-endemic setting."}, {"key": "pop_syphilis", "label": "Syphilis prevalence 5% or higher", "value": null, "description": "The prevalence of syphilis in pregnant women in the population is 5% or higher."}, {"key": "pop_hepb", "label": "Hep B prevalence is intermediate (2% or higher) or high (5% or higher)", "value": null, "description": "The proportion of Hepatitis B surface antigen (HBsAg) seroprevalance in the general population is 2% or higher."}, {"key": "pop_hepb_screening", "label": "National Hep B ANC routine screening program established", "value": null, "description": "There is a national Hepatitis B ANC routine screening program in place."}, {"key": "pop_hepc", "label": "Hep C prevalence is intermediate (2% or higher) or high (5% or higher)", "value": null, "description": "The proportion of Hepatitis C virus (HCV) antibody seroprevalence in the general population is 2% or higher. "}], "documentId": "settings-document-id-2", "identifier": "population_characteristics", "serverVersion": null}'),
(2,	'{"_id": "settings-document-id-1", "_rev": "v4", "team": "Bukesa", "type": "SettingConfiguration", "teamId": "6c8d2b9b-2246-47c2-949b-4fe29e888cc8", "settings": [{"key": "site_ipv_assess", "label": "Minimum requirements for IPV4 assessment", "value": "false", "description": "Are all of the following in place at your facility: \\r\null\\t\\ta. A protocol or standard operating procedure for Intimate Partner Violence (IPV); \\r\null\\t\\tb. A health worker trained on how to ask about IPV and how to provide the minimum response or beyond;\\r\null\\t\\tc. A private setting; \\r\null\\t\\td. A way to ensure confidentiality; \\r\null\\t\\te. Time to allow for appropriate disclosure; and\\r\null\\t\\tf. A system for referral in place. "}, {"key": "site_anc_hiv", "label": "Generalized HIV epidemic", "value": "false", "description": "Is the HIV prevalence consistently > 1% in pregnant women attending antenatal clinics at your facility?"}, {"key": "site_ultrasound", "label": "Ultrasound available", "value": "false", "description": "Is an ultrasound machine available and functional at your facility and a trained health worker available to use it?"}, {"key": "site_bp_tool", "label": "Automated BP measurement tool", "value": "true", "description": "Does your facility use an automated blood pressure (BP) measurement tool?"}], "identifier": "site_characteristics", "locationId": "44de66fb-e6c6-4bae-92bb-386dfe626eba", "providerId": "demo", "dateCreated": "2019-02-27T11:44:54.530+03:00", "serverVersion": 1551438162336}'),
(3,	'{"_id": "5", "_rev": "v1", "type": "SettingConfiguration", "teamId": "131869c5-d67c-4060-8fe1-2dee6041da22", "settings": [{"key": "site_ipv_assess", "label": "Minimum requirements for IPV assessment", "value": "true", "description": "Are all of the following in place at your facility: \\r\null\\t\\ta. A protocol or standard operating procedure for Intimate Partner Violence (IPV); \\r\null\\t\\tb. A health worker trained on how to ask about IPV and how to provide the minimum response or beyond;\\r\null\\t\\tc. A private setting; \\r\null\\t\\td. A way to ensure confidentiality; \\r\null\\t\\te. Time to allow for appropriate disclosure; and\\r\null\\t\\tf. A system for referral in place. "}, {"key": "site_anc_hiv", "label": "Generalized HIV epidemic", "value": "true", "description": "Is the HIV prevalence consistently > 1% in pregnant women attending antenatal clinics at your facility?"}, {"key": "site_ultrasound", "label": "Ultrasound available", "value": "true", "description": "Is an ultrasound machine available and functional at your facility and a trained health worker available to use it?"}, {"key": "site_bp_tool", "label": "Automated BP measurement tool", "value": "false", "description": "Does your facility use an automated blood pressure (BP) measurement tool?"}], "identifier": "site_characteristics", "locationId": "37faf3f3-3ec6-4023-8d8c-dac7f8659dda", "providerId": "demo1", "dateCreated": "2018-10-15T17:39:37.561+03:00", "serverVersion": 1539614334672}'),
(4,	'{"_id": "4", "_rev": "v1", "type": "SettingConfiguration", "teamId": "24f24d29-d3f3-4c91-868a-29e80efab829", "settings": [{"key": "site_ipv_assess", "label": "Minimum requirements for IPV assessment", "value": null, "description": "Are all of the following in place at your facility: \\r\null\\t\\ta. A protocol or standard operating procedure for Intimate Partner Violence (IPV); \\r\null\\t\\tb. A health worker trained on how to ask about IPV and how to provide the minimum response or beyond;\\r\null\\t\\tc. A private setting; \\r\null\\t\\td. A way to ensure confidentiality; \\r\null\\t\\te. Time to allow for appropriate disclosure; and\\r\null\\t\\tf. A system for referral in place. "}, {"key": "site_anc_hiv", "label": "Generalized HIV epidemic", "value": null, "description": "Is the HIV prevalence consistently > 1% in pregnant women attending antenatal clinics at your facility?"}, {"key": "site_ultrasound", "label": "Ultrasound available", "value": null, "description": "Is an ultrasound machine available and functional at your facility and a trained health worker available to use it?"}, {"key": "site_bp_tool", "label": "Automated BP measurement tool", "value": null, "description": "Does your facility use an automated blood pressure (BP) measurement tool?"}], "identifier": "site_characteristics", "locationId": "", "providerId": "", "dateCreated": "1970-10-04T10:17:09.993+03:00", "serverVersion": 1}'),
(5,	'{"_id": "3", "_rev": "v1", "type": "SettingConfiguration", "teamId": "f4d3be2c-c663-43e1-a670-462ca8c7a96c", "settings": [{"key": "site_ipv_assess", "label": "Minimum requirements for IPV assessment", "value": null, "description": "Are all of the following in place at your facility: \\r\null\\t\\ta. A protocol or standard operating procedure for Intimate Partner Violence (IPV); \\r\null\\t\\tb. A health worker trained on how to ask about IPV and how to provide the minimum response or beyond;\\r\null\\t\\tc. A private setting; \\r\null\\t\\td. A way to ensure confidentiality; \\r\null\\t\\te. Time to allow for appropriate disclosure; and\\r\null\\t\\tf. A system for referral in place. "}, {"key": "site_anc_hiv", "label": "Generalized HIV epidemic", "value": null, "description": "Is the HIV prevalence consistently > 1% in pregnant women attending antenatal clinics at your facility?"}, {"key": "site_ultrasound", "label": "Ultrasound available", "value": null, "description": "Is an ultrasound machine available and functional at your facility and a trained health worker available to use it?"}, {"key": "site_bp_tool", "label": "Automated BP measurement tool", "value": null, "description": "Does your facility use an automated blood pressure (BP) measurement tool?"}], "identifier": "site_characteristics", "locationId": "", "providerId": "", "dateCreated": "1970-10-04T10:17:09.993+03:00", "serverVersion": 1}'),
(6,	'{"_id": "3", "_rev": "v1", "type": "SettingConfiguration", "teamId": "73f7cf54-42b5-4cb6-b601-be151d34a9d8", "settings": [{"key": "site_ipv_assess", "label": "Minimum requirements for IPV assessment", "value": null, "description": "Are all of the following in place at your facility: \\r\null\\t\\ta. A protocol or standard operating procedure for Intimate Partner Violence (IPV); \\r\null\\t\\tb. A health worker trained on how to ask about IPV and how to provide the minimum response or beyond;\\r\null\\t\\tc. A private setting; \\r\null\\t\\td. A way to ensure confidentiality; \\r\null\\t\\te. Time to allow for appropriate disclosure; and\\r\null\\t\\tf. A system for referral in place. "}, {"key": "site_anc_hiv", "label": "Generalized HIV epidemic", "value": null, "description": "Is the HIV prevalence consistently > 1% in pregnant women attending antenatal clinics at your facility?"}, {"key": "site_ultrasound", "label": "Ultrasound available", "value": null, "description": "Is an ultrasound machine available and functional at your facility and a trained health worker available to use it?"}, {"key": "site_bp_tool", "label": "Automated BP measurement tool", "value": null, "description": "Does your facility use an automated blood pressure (BP) measurement tool?"}], "identifier": "site_characteristics", "locationId": "", "providerId": "", "dateCreated": "1970-10-04T10:17:09.993+03:00", "serverVersion": 1}'),
(7,	'{"_id": "15", "_rev": "v1", "type": "SettingConfiguration", "teamId": "7e104eee-ec8a-4733-bcf7-c02c51cf43f4", "settings": [{"key": "location_buffer_radius_in_metres", "label": "Location Buffer", "value": "200", "description": ""}], "identifier": "global_configs", "providerId": "demo", "serverVersion": 1551187233614}'),
(8,	'{"_id": "31972118-c8fc-44c4-bdfb-55607022d17a", "_rev": "v1", "type": "SettingConfiguration", "teamId": "6c8d2b9b-2246-47c2-949b-4fe29e888cc8", "settings": [{"key": "king", "label": "Mufasa", "value": "100", "description": ""}, {"key": "prince", "label": "Simba", "value": "200", "description": ""}], "identifier": "lion_king_cast", "serverVersion": 1551438494776}'),
(9,	'{"_id": "affc7614-a51b-4b5f-877a-ad932b38bf4b", "_rev": "v1", "type": "SettingConfiguration", "teamId": "6c8d2b9b-2246-47c2-949b-4fe29e888cc8", "settings": [{"key": "king", "label": "Mufasa", "value": "4800", "description": ""}, {"key": "prince", "label": "Simba", "value": "9000", "description": ""}], "identifier": "lion_king_cast", "serverVersion": 1551439200276}'),
(10,'{"_id": "1512", "_rev": "v11", "type": "SettingConfiguration", "settings": [{"key": "location_buffer_radius_in_metres1", "label": "Location Buffer1", "value": "2001", "description": ""}], "identifier": "global_configs1", "providerId": "demo1", "serverVersion": 1551187233614}'),
(11,'{"_id": "1512", "_rev": "v112", "type": "SettingConfiguration", "settings": [{"key": "location_buffer_radius_in_metres12", "label": "Location Buffer12", "value": "20012", "description": ""}], "identifier": "global_configs12", "providerId": "demo12", "serverVersion": 1551187233614}'),
(12,'{"_id": "1512", "_rev": "v113", "type": "SettingConfiguration", "settings": [{"key": "location_buffer_radius_in_metres13", "label": "Location Buffer13", "value": "20013", "description": ""}], "identifier": "global_configs13", "providerId": "demo13", "serverVersion": 1551187233614}'),
(13,'{"_id": "151", "_rev": "v113", "type": "SettingConfiguration", "settings": [{"key": "location_buffer_radius_in_metres13", "label": "Location Buffer13", "value": "20013", "description": ""}], "identifier": "global_configs13", "providerId": "demo13", "serverVersion": 1551187233614}'),
(14, '{"_id": "1213", "_rev": "v113", "type": "SettingConfiguration", "identifier": "global_configs13", "serverVersion": 1551187233614}');

INSERT INTO core.settings_metadata (id, settings_id, document_id, identifier, server_version, team, team_id, provider_id, location_id, uuid, json, setting_type, setting_value, setting_key, setting_description, setting_label, inherited_from) VALUES
(1, 1, 'settings-document-id-2', 'population_characteristics', null, null, null, null, null, null, null, null, null, null,
 null, null, null),
(2, 2, 'settings-document-id-1', 'site_characteristics', 1551438162336, 'Bukesa', '6c8d2b9b-2246-47c2-949b-4fe29e888cc8',
 'demo', '44de66fb-e6c6-4bae-92bb-386dfe626eba', null, null, null, null, null, null, null, null),
(3, 8, '31972118-c8fc-44c4-bdfb-55607022d17a', 'lion_king_cast', null, null, '6c8d2b9b-2246-47c2-949b-4fe29e888cc8', null,
 null, null, null, null, null, null, null, null, null),
(4, 9, 'affc7614-a51b-4b5f-877a-ad932b38bf4b', 'lion_king_cast_2', 1551439200276, null,
 '6c8d2b9b-2246-47c2-949b-4fe29e888cc8', null, null, null, null, null, null, null, null, null, null),
(5, 4, 'settings-document-id-4', 'site_characteristics', 1, null, '24f24d29-d3f3-4c91-868a-29e80efab829', null, null,
 null, null, null, null, null, null, null, null),
(6, 5, 'settings-document-id-5', 'site_characteristics', 1, null, '131869c5-d67c-4060-8fe1-2dee6041da22', null, null,
 null, null, null, null, null, null, null, null),
(7, 6, 'settings-document-id-6', 'site_characteristics', null, null, '73f7cf54-42b5-4cb6-b601-be151d34a9d8', null, null,
 null, null, null, null, null, null, null, null),
(8, 7, '0811b1df-ccaf-476f-8b36-5a489d0391d6', 'global_configs', 1, 'my-team', '7e104eee-ec8a-4733-bcf7-c02c51cf43f4',
 'demo', null, null, null, null, null, null, null, null, null),
(9, 3, 'settings-document-id-3', 'site_characteristics', 1, null, 'f4d3be2c-c663-43e1-a670-462ca8c7a96c', null, null,
 null, null, null, null, null, null, null, null),
(10, 10, '1512', 'global_configs1', 1, 'my-team', 'f4d3be2c-c663-43e1-a670-462ca8c7a96c', null, null,
 '7e2b3029-a79b-4479-9296-938f01ac5bcd', null, 'SettingsConfiguration', 'false', 'test_1', 'My tests', 'Lots of tests', null),
(11, 11, '151', 'global_configs1', 1, 'my-team', 'f4d3be2c-c663-43e1-a670-462ca8c7a96c', null, null,
 '7e3b3020-a79b-4479-9296-938f01ac5bcd', null, 'SettingsConfiguration', 'true', 'test_2', 'my tests', 'lots of new tests', null),
(12, 12, '151', 'global_configs13', 1, 'my-team', 'f4d3be2c-c663-43e1-a670-462ca8c7a96c', null, null,
 '7e2b3029-a79b-4479-7296-938f01ac5bcd', null, 'SettingsConfiguration', 'false', 'test_3', 'my tests', 'Lots and lots ofnew tests', null),
(13, 13, '151', 'global_configs1', 1, 'my-team', 'f4d3be2c-c663-43e1-a670-462ca8c7a96c', null, null,
    '7e2b3029-a79b-4479-7296-938f01ac5bcd', null, 'SettingsConfiguration', 'false', 'test_3', 'my tests', 'Lots and lots of new tests', null),
(14, 14, '1213', 'global_configs13', 1, null, null, null, null, '7e2b3029-a79b-4479-7196-938f01ac5bcd', null,
 'SettingsConfiguration', '13', 'test_4', 'my tests', 'Lots and lots of new tests', null),
(15, 14, '1213', 'global_configs13', 1, null, null, null, null, '7e2b3029-a79b-4479-7196-938f01ac5bcd', null,
    'SettingsConfiguration', '13', 'test_5', 'my tests', 'Lots and lots of new tests', null),
(16, 14, '1213', 'global_configs13', 1, null, null, null, null, '7e2b3029-a79b-4479-7196-938f01ac5bcd', null,
    'SettingsConfiguration', '13', 'test_6', 'my tests', 'Lots and lots of new tests', null);

    SELECT setval('core.setting_server_version_seq',(SELECT max(json->>'serverVersion')::bigint+1 FROM core.settings));