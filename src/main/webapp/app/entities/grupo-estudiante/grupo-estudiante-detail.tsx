import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './grupo-estudiante.reducer';

export const GrupoEstudianteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const grupoEstudianteEntity = useAppSelector(state => state.grupoEstudiante.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="grupoEstudianteDetailsHeading">
          <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.detail.title">GrupoEstudiante</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{grupoEstudianteEntity.id}</dd>
          <dt>
            <span id="grupoEstudianteId">
              <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.grupoEstudianteId">Grupo Estudiante Id</Translate>
            </span>
          </dt>
          <dd>{grupoEstudianteEntity.grupoEstudianteId}</dd>
          <dt>
            <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.grupo">Grupo</Translate>
          </dt>
          <dd>{grupoEstudianteEntity.grupo ? grupoEstudianteEntity.grupo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/grupo-estudiante" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grupo-estudiante/${grupoEstudianteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GrupoEstudianteDetail;
