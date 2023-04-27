import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGrupo } from 'app/shared/model/grupo.model';
import { getEntities as getGrupos } from 'app/entities/grupo/grupo.reducer';
import { IGrupoEstudiante } from 'app/shared/model/grupo-estudiante.model';
import { getEntity, updateEntity, createEntity, reset } from './grupo-estudiante.reducer';

export const GrupoEstudianteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const grupos = useAppSelector(state => state.grupo.entities);
  const grupoEstudianteEntity = useAppSelector(state => state.grupoEstudiante.entity);
  const loading = useAppSelector(state => state.grupoEstudiante.loading);
  const updating = useAppSelector(state => state.grupoEstudiante.updating);
  const updateSuccess = useAppSelector(state => state.grupoEstudiante.updateSuccess);

  const handleClose = () => {
    navigate('/grupo-estudiante');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGrupos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...grupoEstudianteEntity,
      ...values,
      grupo: grupos.find(it => it.id.toString() === values.grupo.toString()),
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
          ...grupoEstudianteEntity,
          grupo: grupoEstudianteEntity?.grupo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="labArquiJHipsterApp.grupoEstudiante.home.createOrEditLabel" data-cy="GrupoEstudianteCreateUpdateHeading">
            <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.home.createOrEditLabel">Create or edit a GrupoEstudiante</Translate>
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
                  id="grupo-estudiante-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('labArquiJHipsterApp.grupoEstudiante.grupoEstudianteId')}
                id="grupo-estudiante-grupoEstudianteId"
                name="grupoEstudianteId"
                data-cy="grupoEstudianteId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="grupo-estudiante-grupo"
                name="grupo"
                data-cy="grupo"
                label={translate('labArquiJHipsterApp.grupoEstudiante.grupo')}
                type="select"
              >
                <option value="" key="0" />
                {grupos
                  ? grupos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/grupo-estudiante" replace color="info">
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

export default GrupoEstudianteUpdate;
