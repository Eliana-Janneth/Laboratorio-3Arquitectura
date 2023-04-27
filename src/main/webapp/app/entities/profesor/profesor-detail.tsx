import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profesor.reducer';

export const ProfesorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profesorEntity = useAppSelector(state => state.profesor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profesorDetailsHeading">
          <Translate contentKey="labArquiJHipsterApp.profesor.detail.title">Profesor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.id}</dd>
          <dt>
            <span id="profesorId">
              <Translate contentKey="labArquiJHipsterApp.profesor.profesorId">Profesor Id</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.profesorId}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="labArquiJHipsterApp.profesor.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.nombre}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="labArquiJHipsterApp.profesor.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.tipo}</dd>
          <dt>
            <span id="correoElectronico">
              <Translate contentKey="labArquiJHipsterApp.profesor.correoElectronico">Correo Electronico</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.correoElectronico}</dd>
          <dt>
            <span id="usuarioPortal">
              <Translate contentKey="labArquiJHipsterApp.profesor.usuarioPortal">Usuario Portal</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.usuarioPortal}</dd>
          <dt>
            <Translate contentKey="labArquiJHipsterApp.profesor.grupos">Grupos</Translate>
          </dt>
          <dd>{profesorEntity.grupos ? profesorEntity.grupos.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/profesor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profesor/${profesorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfesorDetail;
