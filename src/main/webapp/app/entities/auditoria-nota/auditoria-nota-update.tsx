import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfesor } from 'app/shared/model/profesor.model';
import { getEntities as getProfesors } from 'app/entities/profesor/profesor.reducer';
import { INota } from 'app/shared/model/nota.model';
import { getEntities as getNotas } from 'app/entities/nota/nota.reducer';
import { IAuditoriaNota } from 'app/shared/model/auditoria-nota.model';
import { getEntity, updateEntity, createEntity, reset } from './auditoria-nota.reducer';

export const AuditoriaNotaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const profesors = useAppSelector(state => state.profesor.entities);
  const notas = useAppSelector(state => state.nota.entities);
  const auditoriaNotaEntity = useAppSelector(state => state.auditoriaNota.entity);
  const loading = useAppSelector(state => state.auditoriaNota.loading);
  const updating = useAppSelector(state => state.auditoriaNota.updating);
  const updateSuccess = useAppSelector(state => state.auditoriaNota.updateSuccess);

  const handleClose = () => {
    navigate('/auditoria-nota');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfesors({}));
    dispatch(getNotas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...auditoriaNotaEntity,
      ...values,
      profesor: profesors.find(it => it.id.toString() === values.profesor.toString()),
      nota: notas.find(it => it.id.toString() === values.nota.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...auditoriaNotaEntity,
          profesor: auditoriaNotaEntity?.profesor?.id,
          nota: auditoriaNotaEntity?.nota?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="labArquiJHipsterApp.auditoriaNota.home.createOrEditLabel" data-cy="AuditoriaNotaCreateUpdateHeading">
            <Translate contentKey="labArquiJHipsterApp.auditoriaNota.home.createOrEditLabel">Create or edit a AuditoriaNota</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="auditoria-nota-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('labArquiJHipsterApp.auditoriaNota.auditoriaId')}
                id="auditoria-nota-auditoriaId"
                name="auditoriaId"
                data-cy="auditoriaId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.auditoriaNota.notaAnterior')}
                id="auditoria-nota-notaAnterior"
                name="notaAnterior"
                data-cy="notaAnterior"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.auditoriaNota.notaNueva')}
                id="auditoria-nota-notaNueva"
                name="notaNueva"
                data-cy="notaNueva"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.auditoriaNota.fechaModificacion')}
                id="auditoria-nota-fechaModificacion"
                name="fechaModificacion"
                data-cy="fechaModificacion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.auditoriaNota.ip')}
                id="auditoria-nota-ip"
                name="ip"
                data-cy="ip"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.auditoriaNota.usuarioPortal')}
                id="auditoria-nota-usuarioPortal"
                name="usuarioPortal"
                data-cy="usuarioPortal"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="auditoria-nota-profesor"
                name="profesor"
                data-cy="profesor"
                label={translate('labArquiJHipsterApp.auditoriaNota.profesor')}
                type="select"
              >
                <option value="" key="0" />
                {profesors
                  ? profesors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="auditoria-nota-nota"
                name="nota"
                data-cy="nota"
                label={translate('labArquiJHipsterApp.auditoriaNota.nota')}
                type="select"
              >
                <option value="" key="0" />
                {notas
                  ? notas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/auditoria-nota" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AuditoriaNotaUpdate;
