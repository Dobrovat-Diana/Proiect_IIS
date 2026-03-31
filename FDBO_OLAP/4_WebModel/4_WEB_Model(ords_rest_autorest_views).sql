--Script pentru activarea schemei în ORDS

BEGIN
  ORDS.ENABLE_SCHEMA(
    p_enabled             => TRUE,
    p_schema              => 'SALES_SRC',
    p_url_mapping_type    => 'BASE_PATH',
    p_url_mapping_pattern => 'sales',
    p_auto_rest_auth      => FALSE
  );
  COMMIT;
END;
/

--Script pentru publicarea view-ului V_CONSOLIDATED_SALES

BEGIN
  ORDS.ENABLE_OBJECT(
    p_enabled        => TRUE,
    p_schema         => 'SALES_SRC',
    p_object         => 'V_CONSOLIDATED_SALES',
    p_object_type    => 'VIEW',
    p_object_alias   => 'consolidated-sales',
    p_auto_rest_auth => FALSE
  );
  COMMIT;
END;
/

--Script pentru publicarea view-ului FACT_SALES

BEGIN
  ORDS.ENABLE_OBJECT(
    p_enabled        => TRUE,
    p_schema         => 'SALES_SRC',
    p_object         => 'FACT_SALES',
    p_object_type    => 'VIEW',
    p_object_alias   => 'fact-sales',
    p_auto_rest_auth => FALSE
  );
  COMMIT;
END;
/

--Script pentru publicarea view-ului V_SALES_CUBE

BEGIN
  ORDS.ENABLE_OBJECT(
    p_enabled        => TRUE,
    p_schema         => 'SALES_SRC',
    p_object         => 'V_SALES_CUBE',
    p_object_type    => 'VIEW',
    p_object_alias   => 'sales-cube',
    p_auto_rest_auth => FALSE
  );
  COMMIT;
END;
/

--Script pentru publicarea view-ului V_SALES_ROLLUP_TIME

BEGIN
  ORDS.ENABLE_OBJECT(
    p_enabled        => TRUE,
    p_schema         => 'SALES_SRC',
    p_object         => 'V_SALES_ROLLUP_TIME',
    p_object_type    => 'VIEW',
    p_object_alias   => 'sales-rollup-time',
    p_auto_rest_auth => FALSE
  );
  COMMIT;
END;
/
