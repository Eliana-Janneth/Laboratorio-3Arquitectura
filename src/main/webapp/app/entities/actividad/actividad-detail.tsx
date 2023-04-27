import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './actividad.reducer';

export const ActividadDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const actividadEntity = useAppSelector(state => state.actividad.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="actividadDetailsHeading">
          <Translate contentKey="labArquiJHipsterApp.actividad.detail.title">Actividad</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{actividadEntity.id}</dd>
          <dt>
            <span id="actividadId">
              <Translate contentKey="labArquiJHipsterApp.actividad.actividadId">Actividad Id</Translate>
            </span>
          </dt>
          <dd>{actividadEntity.actividadId}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="labArquiJHipsterApp.actividad.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{actividadEntity.descripcion}</dd>
          <dt>
            <span id="porcentaje">
              <Translate contentKey="labArquiJHipsterApp.actividad.porcentaje">Porcentaje</Translate>
            </span>
          </dt>
          <dd>{actividadEntity.porcentaje}</dd>
          <dt>
            <Translate contentKey="labArquiJHipsterApp.actividad.grupo">Grupo</Translate>
          </dt>
          <dd>{actividadEntity.grupo ? actividadEntity.grupo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/actividad" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/actividad/${actividadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActividadDetail;
