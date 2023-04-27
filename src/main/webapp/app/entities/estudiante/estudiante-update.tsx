import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGrupoEstudiante } from 'app/shared/model/grupo-estudiante.model';
import { getEntities as getGrupoEstudiantes } from 'app/entities/grupo-estudiante/grupo-estudiante.reducer';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { getEntity, updateEntity, createEntity, reset } from './estudiante.reducer';

export const EstudianteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const grupoEstudiantes = useAppSelector(state => state.grupoEstudiante.entities);
  const estudianteEntity = useAppSelector(state => state.estudiante.entity);
  const loading = useAppSelector(state => state.estudiante.loading);
  const updating = useAppSelector(state => state.estudiante.updating);
  const updateSuccess = useAppSelector(state => state.estudiante.updateSuccess);

  const handleClose = () => {
    navigate('/estudiante');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGrupoEstudiantes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...estudianteEntity,
      ...values,
      gruposEstudiante: grupoEstudiantes.find(it => it.id.toString() === values.gruposEstudiante.toString()),
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
          ...estudianteEntity,
          gruposEstudiante: estudianteEntity?.gruposEstudiante?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="labArquiJHipsterApp.estudiante.home.createOrEditLabel" data-cy="EstudianteCreateUpdateHeading">
            <Translate contentKey="labArquiJHipsterApp.estudiante.home.createOrEditLabel">Create or edit a Estudiante</Translate>
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
                  id="estudiante-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('labArquiJHipsterApp.estudiante.estudianteId')}
                id="estudiante-estudianteId"
                name="estudianteId"
                data-cy="estudianteId"
                type="text"
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.estudiante.nombre')}
                id="estudiante-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="estudiante-gruposEstudiante"
                name="gruposEstudiante"
                data-cy="gruposEstudiante"
                label={translate('labArquiJHipsterApp.estudiante.gruposEstudiante')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/estudiante" replace color="info">
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

export default EstudianteUpdate;
