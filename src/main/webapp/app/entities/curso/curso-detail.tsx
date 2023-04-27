import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './curso.reducer';

export const CursoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cursoEntity = useAppSelector(state => state.curso.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cursoDetailsHeading">
          <Translate contentKey="labArquiJHipsterApp.curso.detail.title">Curso</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cursoEntity.id}</dd>
          <dt>
            <span id="cursoId">
              <Translate contentKey="labArquiJHipsterApp.curso.cursoId">Curso Id</Translate>
            </span>
          </dt>
          <dd>{cursoEntity.cursoId}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="labArquiJHipsterApp.curso.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{cursoEntity.codigo}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="labArquiJHipsterApp.curso.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{cursoEntity.nombre}</dd>
          <dt>
            <span id="credito">
              <Translate contentKey="labArquiJHipsterApp.curso.credito">Credito</Translate>
            </span>
          </dt>
          <dd>{cursoEntity.credito}</dd>
        </dl>
        <Button tag={Link} to="/curso" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/curso/${cursoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CursoDetail;
