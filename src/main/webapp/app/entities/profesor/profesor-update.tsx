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
import { IProfesor } from 'app/shared/model/profesor.model';
import { getEntity, updateEntity, createEntity, reset } from './profesor.reducer';

export const ProfesorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const grupos = useAppSelector(state => state.grupo.entities);
  const profesorEntity = useAppSelector(state => state.profesor.entity);
  const loading = useAppSelector(state => state.profesor.loading);
  const updating = useAppSelector(state => state.profesor.updating);
  const updateSuccess = useAppSelector(state => state.profesor.updateSuccess);

  const handleClose = () => {
    navigate('/profesor');
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
      ...profesorEntity,
      ...values,
      grupos: grupos.find(it => it.id.toString() === values.grupos.toString()),
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
          ...profesorEntity,
          grupos: profesorEntity?.grupos?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="labArquiJHipsterApp.profesor.home.createOrEditLabel" data-cy="ProfesorCreateUpdateHeading">
            <Translate contentKey="labArquiJHipsterApp.profesor.home.createOrEditLabel">Create or edit a Profesor</Translate>
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
                  id="profesor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('labArquiJHipsterApp.profesor.profesorId')}
                id="profesor-profesorId"
                name="profesorId"
                data-cy="profesorId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.profesor.nombre')}
                id="profesor-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.profesor.tipo')}
                id="profesor-tipo"
                name="tipo"
                data-cy="tipo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.profesor.correoElectronico')}
                id="profesor-correoElectronico"
                name="correoElectronico"
                data-cy="correoElectronico"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.profesor.usuarioPortal')}
                id="profesor-usuarioPortal"
                name="usuarioPortal"
                data-cy="usuarioPortal"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="profesor-grupos"
                name="grupos"
                data-cy="grupos"
                label={translate('labArquiJHipsterApp.profesor.grupos')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/profesor" replace color="info">
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

export default ProfesorUpdate;
