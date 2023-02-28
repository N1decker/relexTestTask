create type dml_type as ENUM ('INSERT', 'UPDATE', 'DELETE');

create table if not exists wallet_audit_log
(
    wallet_id      bigint       not null,
    old_row_data   jsonb,
    new_row_data   jsonb,
    dml_type       dml_type     not null,
    dml_timestamp  timestamp    not null,
    dml_created_by varchar(255) not null,
    primary key (wallet_id, dml_type, dml_timestamp)
);

create
    or replace function wallet_audit_trigger_func()
    returns trigger as
$body$
BEGIN
    if
        (tg_op = 'INSERT') then
        insert into wallet_audit_log (wallet_id,
                                      old_row_data,
                                      new_row_data,
                                      dml_type,
                                      dml_timestamp,
                                      dml_created_by)
        values (new.id,
                null,
                to_jsonb(new),
                'INSERT',
                CURRENT_TIMESTAMP,
                new.user_id);
        return new;
    elseif
        (tg_op = 'UPDATE') then
        insert into wallet_audit_log (wallet_id,
                                      old_row_data,
                                      new_row_data,
                                      dml_type,
                                      dml_timestamp,
                                      dml_created_by)
        values (new.id,
                to_jsonb(old),
                to_jsonb(new),
                'UPDATE',
                CURRENT_TIMESTAMP,
                new.user_id);

        return new;
    elseif
        (tg_op = 'DELETE') then
        insert into wallet_audit_log (wallet_id,
                                      old_row_data,
                                      new_row_data,
                                      dml_type,
                                      dml_timestamp,
                                      dml_created_by)
        values (new.id,
                to_jsonb(old),
                null,
                'DELETE',
                CURRENT_TIMESTAMP,
                new.user_id);

        return new;
    end if;
END;
$body$
    language plpgsql;

create trigger wallet_audit_trigger
    after insert or
        update or
        delete
    on wallet
    for each row
execute function wallet_audit_trigger_func();