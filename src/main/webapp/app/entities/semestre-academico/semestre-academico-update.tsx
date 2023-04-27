import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISemestreAcademico } from 'app/shared/model/semestre-academico.model';
import { getEntity, updateEntity, createEntity, reset } from './semestre-academico.reducer';

export const SemestreAcademicoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const semestreAcademicoEntity = useAppSelector(state => state.semestreAcademico.entity);
  const loading = useAppSelector(state => state.semestreAcademico.loading);
  const updating = useAppSelector(state => state.semestreAcademico.updating);
  const updateSuccess = useAppSelector(state => state.semestreAcademico.updateSuccess);

  const handleClose = () => {
    navigate('/semestre-academico');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...semestreAcademicoEntity,
      ...values,
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
          ...semestreAcademicoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="labArquiJHipsterApp.semestreAcademico.home.createOrEditLabel" data-cy="SemestreAcademicoCreateUpdateHeading">
            <Translate contentKey="labArquiJHipsterApp.semestreAcademico.home.createOrEditLabel">
              Create or edit a SemestreAcademico
            </Translate>
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
                  id="semestre-academico-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('labArquiJHipsterApp.semestreAcademico.semestreAcademicoId')}
                id="semestre-academico-semestreAcademicoId"
                name="semestreAcademicoId"
                data-cy="semestreAcademicoId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.semestreAcademico.codigo')}
                id="semestre-academico-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.semestreAcademico.estado')}
                id="semestre-academico-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.semestreAcademico.anio')}
                id="semestre-academico-anio"
                name="anio"
                data-cy="anio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.semestreAcademico.periodoAcademico')}
                id="semestre-academico-periodoAcademico"
                name="periodoAcademico"
                data-cy="periodoAcademico"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/semestre-academico" replace color="info">
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

export default SemestreAcademicoUpdate;
