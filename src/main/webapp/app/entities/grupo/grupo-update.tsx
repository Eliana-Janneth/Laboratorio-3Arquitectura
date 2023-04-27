import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurso } from 'app/shared/model/curso.model';
import { getEntities as getCursos } from 'app/entities/curso/curso.reducer';
import { ISemestreAcademico } from 'app/shared/model/semestre-academico.model';
import { getEntities as getSemestreAcademicos } from 'app/entities/semestre-academico/semestre-academico.reducer';
import { IGrupo } from 'app/shared/model/grupo.model';
import { getEntity, updateEntity, createEntity, reset } from './grupo.reducer';

export const GrupoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cursos = useAppSelector(state => state.curso.entities);
  const semestreAcademicos = useAppSelector(state => state.semestreAcademico.entities);
  const grupoEntity = useAppSelector(state => state.grupo.entity);
  const loading = useAppSelector(state => state.grupo.loading);
  const updating = useAppSelector(state => state.grupo.updating);
  const updateSuccess = useAppSelector(state => state.grupo.updateSuccess);

  const handleClose = () => {
    navigate('/grupo');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCursos({}));
    dispatch(getSemestreAcademicos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...grupoEntity,
      ...values,
      curso: cursos.find(it => it.id.toString() === values.curso.toString()),
      semestreAcademico: semestreAcademicos.find(it => it.id.toString() === values.semestreAcademico.toString()),
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
          ...grupoEntity,
          curso: grupoEntity?.curso?.id,
          semestreAcademico: grupoEntity?.semestreAcademico?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="labArquiJHipsterApp.grupo.home.createOrEditLabel" data-cy="GrupoCreateUpdateHeading">
            <Translate contentKey="labArquiJHipsterApp.grupo.home.createOrEditLabel">Create or edit a Grupo</Translate>
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
                  id="grupo-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('labArquiJHipsterApp.grupo.grupoId')}
                id="grupo-grupoId"
                name="grupoId"
                data-cy="grupoId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.grupo.codigo')}
                id="grupo-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('labArquiJHipsterApp.grupo.horario')}
                id="grupo-horario"
                name="horario"
                data-cy="horario"
                type="text"
              />
              <ValidatedField
                id="grupo-curso"
                name="curso"
                data-cy="curso"
                label={translate('labArquiJHipsterApp.grupo.curso')}
                type="select"
              >
                <option value="" key="0" />
                {cursos
                  ? cursos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="grupo-semestreAcademico"
                name="semestreAcademico"
                data-cy="semestreAcademico"
                label={translate('labArquiJHipsterApp.grupo.semestreAcademico')}
                type="select"
              >
                <option value="" key="0" />
                {semestreAcademicos
                  ? semestreAcademicos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/grupo" replace color="info">
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

export default GrupoUpdate;
