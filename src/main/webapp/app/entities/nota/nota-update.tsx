import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActividad } from 'app/shared/model/actividad.model';
import { getEntities as getActividads } from 'app/entities/actividad/actividad.reducer';
import { IGrupoEstudiante } from 'app/shared/model/grupo-estudiante.model';
import { getEntities as getGrupoEstudiantes } from 'app/entities/grupo-estudiante/grupo-estudiante.reducer';
import { INota } from 'app/shared/model/nota.model';
import { getEntity, updateEntity, createEntity, reset } from './nota.reducer';

export const NotaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const actividads = useAppSelector(state => state.actividad.entities);
  const grupoEstudiantes = useAppSelector(state => state.grupoEstudiante.entities);
  const notaEntity = useAppSelector(state => state.nota.entity);
  const loading = useAppSelector(state => state.nota.loading);
  const updating = useAppSelector(state => state.nota.updating);
  const updateSuccess = useAppSelector(state => state.nota.updateSuccess);

  const handleClose = () => {
    navigate('/nota');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActividads({}));
    dispatch(getGrupoEstudiantes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...notaEntity,
      ...values,
      actividad: actividads.find(it => it.id.toString() === values.actividad.toString()),
      grupoEstudiante: grupoEstudiantes.find(it => it.id.toString() === values.grupoEstudiante.toString()),
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
          ...notaEntity,
          actividad: notaEntity?.actividad?.id,
          grupoEstudiante: notaEntity?.grupoEstudiante?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="labArquiJHipsterApp.nota.home.createOrEditLabel" data-cy="NotaCreateUpdateHeading">
            <Translate contentKey="labArquiJHipsterApp.nota.home.createOrEditLabel">Create or edit a Nota</Translate>
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
                  id="nota-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('labArquiJHipsterApp.nota.notaId')}
                id="nota-notaId"
                name="notaId"
                data-cy="notaId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.nota.nota')}
                id="nota-nota"
                name="nota"
                data-cy="nota"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.nota.fechaCreacion')}
                id="nota-fechaCreacion"
                name="fechaCreacion"
                data-cy="fechaCreacion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="nota-actividad"
                name="actividad"
                data-cy="actividad"
                label={translate('labArquiJHipsterApp.nota.actividad')}
                type="select"
              >
                <option value="" key="0" />
                {actividads
                  ? actividads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="nota-grupoEstudiante"
                name="grupoEstudiante"
                data-cy="grupoEstudiante"
                label={translate('labArquiJHipsterApp.nota.grupoEstudiante')}
                type="select"
              >
                <option value="" key="0" />
                {grupoEstudiantes
                  ? grupoEstudiantes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nota" replace color="info">
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

export default NotaUpdate;
